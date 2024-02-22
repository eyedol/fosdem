// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.compose.common.ui.api.UiMessage
import com.addhen.fosdem.compose.common.ui.api.UiMessageManager
import com.addhen.fosdem.core.api.screens.SessionBookmarkScreen
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.SessionScreen
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day1Event2
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.model.api.day2Event2
import com.addhen.fosdem.model.api.day2Event3
import com.addhen.fosdem.model.api.sortAndGroupedEventsItems
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.SessionListUiState
import com.addhen.fosdem.ui.session.component.dayTabs
import com.addhen.fosdem.ui.session.component.toDayTab
import com.addhen.fosdem.ui.session.list.component.SessionSheetUiState
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SessionUiPresenterFactory(
  private val presenterFactory: (Navigator) -> SessionPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is SessionScreen -> presenterFactory(navigator)
      else -> null
    }
  }
}

@Inject
class SessionPresenter(
  @Assisted private val navigator: Navigator,
  private val repository: EventsRepository,
) : Presenter<SessionUiState> {
  @Composable
  override fun present(): SessionUiState {
    val scope = rememberCoroutineScope()
    val days by rememberRetained { mutableStateOf(dayTabs) }
    var isRefreshing by rememberRetained { mutableStateOf(false) }
    val uiMessageManager = remember { UiMessageManager() }
    val message by uiMessageManager.message.collectAsState(null)
    val appStrings = LocalStrings.current

    LaunchedEffect(isRefreshing) {
      if (isRefreshing) {
        repository.refresh()
        isRefreshing = false
      }
    }

    val events by repository.getEvents().map { _ ->
      successSessionSheetUiSate(
        listOf(day1Event, day1Event2, day2Event1, day2Event2, day2Event3),
        days,
      )
    }.catch {
      Logger.e(it) { "Error occurred" }
      uiMessageManager.emitMessage(
        UiMessage(
          it,
          actionLabel = appStrings.tryAgain,
        ),
      )
    }
      .collectAsRetainedState(SessionSheetUiState.Loading(days))

    fun eventSink(event: SessionUiEvent) {
      when (event) {
        is SessionUiEvent.GoToSessionDetails -> {
          navigator.goTo(SessionDetailScreen(event.eventId))
        }

        is SessionUiEvent.ToggleSessionBookmark -> {
          scope.launch { repository.toggleBookmark(event.eventId) }
        }

        SessionUiEvent.BookSession -> navigator.goTo(SessionBookmarkScreen)
        SessionUiEvent.RefreshSession -> isRefreshing = true
        SessionUiEvent.ClearMessage -> {
          scope.launch { uiMessageManager.clearMessage(message!!.id) }
        }
      }
    }

    return SessionUiState(
      isRefreshing = isRefreshing,
      content = events,
      message = message,
      eventSink = ::eventSink,
    )
  }

  private fun successSessionSheetUiSate(
    results: List<Event>,
    days: PersistentList<DayTab>,
  ): SessionSheetUiState {
    if (results.isEmpty()) return SessionSheetUiState.Empty(days)

    val sessionGroupedAndMapWithDays = groupAndMapEventsWithDays(results)
    return SessionSheetUiState.ListSession(
      days = days,
      sessionListUiStates = sessionGroupedAndMapWithDays,
    )
  }

  private fun groupAndMapEventsWithDays(
    events: List<Event>,
  ): PersistentMap<DayTab, SessionListUiState> {
    val groupEventsByDay = events.groupBy { it.day }
    val sessionsWithDays = mutableMapOf<DayTab, SessionListUiState>()
    groupEventsByDay.forEach { (key, events) ->
      val sortedAndGroupEvents = events.sortAndGroupedEventsItems().toPersistentMap()
      sessionsWithDays[key.toDayTab()] = SessionListUiState(sortedAndGroupEvents)
    }
    return sessionsWithDays.toPersistentMap()
  }
}
