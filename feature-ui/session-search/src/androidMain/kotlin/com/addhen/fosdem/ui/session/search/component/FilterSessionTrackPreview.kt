// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day2Event1
import com.addhen.fosdem.ui.session.component.FilterTrack
import com.addhen.fosdem.ui.session.component.toFilterTrack
import kotlinx.collections.immutable.toImmutableList

@MultiThemePreviews
@Composable
private fun FilterSessionTrackPreview() {
  var uiState by remember {
    mutableStateOf(
      SearchFilterUiState(
        selectedItems = emptyList<FilterTrack>().toImmutableList(),
        items = listOf(
          day1Event.track.toFilterTrack(),
          day2Event1.track.toFilterTrack(),
        ).toImmutableList(),
      ),
    )
  }
  AppTheme {
    Surface {
      FilterSessionTrackChip(
        searchFilterUiState = uiState,
        onSessionTracksSelected = { track, isSelected ->
          val selectedCategories = uiState.selectedItems.toMutableList()
          val newSelectedCategories = selectedCategories.apply {
            if (isSelected) {
              add(track)
            } else {
              remove(track)
            }
          }
          uiState = uiState.copy(
            selectedItems = newSelectedCategories.toImmutableList(),
            isSelected = newSelectedCategories.isNotEmpty(),
            selectedValues = newSelectedCategories.joinToString { it.name },
          )
        },
        onFilterSessionTrackChipClicked = {},
      )
    }
  }
}
