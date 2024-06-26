// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.model.api.day
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day2
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.ui.session.list.component.SessionSheet
import com.addhen.fosdem.ui.session.list.component.SessionsSheetUiState
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap

@MultiThemePreviews
@Composable
private fun SessionSheetPreview() {
  val sessionListUiState = SessionListUiState(
    sortAndGroupedEventsItems,
  )

  val sessionListUiState2 = SessionListUiState(
    sortAndGroupedEventsItems2,
  )

  val dayTab = day.toDayTab()
  val dayTab2 = day2.toDayTab()

  val uiState = SessionsSheetUiState.ListSession(
    days = listOf(dayTab, dayTab2).toPersistentList(),
    sessionListUiStates = mapOf(
      dayTab to sessionListUiState,
      dayTab2 to sessionListUiState2,
    ),
  )

  AppTheme {
    Surface {
      SessionSheet(
        uiState = uiState,
        sessionScreenScrollState = SessionScreenScrollState(),
        onSessionItemClick = { _ -> },
        onBookmarkClick = { _ -> },
        contentPadding = PaddingValues(),
      )
    }
  }
}

val sortAndGroupedEventsItems = listOf(day1Event, day2Event1).groupBy {
  it.startAt.toString() + it.duration.toString()
}.mapValues { entries ->
  entries.value.sortedWith(
    compareBy({ it.day.date.toString() }, { it.startAt.toString() }),
  )
}.toPersistentMap()

val sortAndGroupedEventsItems2 = listOf(day2Event1).groupBy {
  it.startAt.toString() + it.duration.toString()
}.mapValues { entries ->
  entries.value.sortedWith(
    compareBy({ it.day.date.toString() }, { it.startAt.toString() }),
  )
}.toPersistentMap()
