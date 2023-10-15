// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.SessionSearchScreen
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.ui.session.search.component.SearchUiState
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import kotlin.time.Duration.Companion.milliseconds

@Inject
class SessionSearchUiPresenterFactory(
  private val presenterFactory: (Navigator) -> SessionSearchPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is SessionSearchScreen -> {
        presenterFactory(navigator)
      }

      else -> null
    }
  }
}

@Inject
class SessionSearchPresenter(
  @Assisted private val navigator: Navigator,
  private val eventsRepository: EventsRepository,
  private val searchUiPresenter: SearchSessionUiPresenter,
) : Presenter<SessionSearchUiState> {
  @Composable
  override fun present(): SessionSearchUiState {
    val scope = rememberCoroutineScope()

    val searchUiState by searchUiPresenter.observeSearchFiltersAction
      .collectAsRetainedState(SearchUiState.Loading())

    fun eventSink(event: SessionSearchUiEvent) {
      when (event) {
        is SessionSearchUiEvent.GoToSessionDetails -> {
          navigator.goTo(SessionDetailScreen(event.eventId))
        }

        is SessionSearchUiEvent.FilterDay -> {
          searchUiPresenter.onDaySelected(event.dayTab, event.isSelected)
        }

        is SessionSearchUiEvent.FilterSessionRoom -> {
          searchUiPresenter.onRoomSelected(event.room, event.isSelected)
        }

        is SessionSearchUiEvent.FilterSessionTrack -> {
          searchUiPresenter.onTrackSelected(event.track, event.isSelected)
        }

        is SessionSearchUiEvent.ToggleSessionBookmark -> {
          scope.launch { eventsRepository.toggleBookmark(event.eventId) }
        }

        is SessionSearchUiEvent.QuerySearch -> {
          scope.launch {
            delay(300.milliseconds) // Debounce effect
            searchUiPresenter.onQueryChanged(event.query)
          }
        }

        SessionSearchUiEvent.GoToPreviousScreen -> navigator.pop()
      }
    }

    return SessionSearchUiState(searchUiState, ::eventSink)
  }
}
