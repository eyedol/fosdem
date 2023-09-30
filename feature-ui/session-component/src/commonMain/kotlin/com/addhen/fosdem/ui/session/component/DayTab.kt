// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Immutable
class DayTab(val id: Long, val date: LocalDate) {
  val title: String
    get() = date.dayOfWeek.toString().lowercase()
      .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
  companion object {
    private val tzBrussels = TimeZone.of("Europe/Brussels")

    val Saver: Saver<DayTab, *> = listSaver(
      save = { listOf(it.id.toString(), it.date.toString()) },
      restore = {
        DayTab(
          id = it.first().toLong(),
          date = LocalDate.parse(it.last().toString()),
        )
      },
    )

    fun selectDay(days: List<DayTab>): DayTab {
      val reversedEntries = days.sortedByDescending { it.id }
      var selectedDay = reversedEntries.last()
      for (entry in reversedEntries) {
        if (Clock.System.now().toLocalDateTime(tzBrussels).dayOfWeek <= entry.date.dayOfWeek) {
          selectedDay = entry
        }
      }
      return selectedDay
    }
  }
}
