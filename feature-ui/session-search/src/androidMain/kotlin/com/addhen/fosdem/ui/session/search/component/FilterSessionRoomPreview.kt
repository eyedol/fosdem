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
import com.addhen.fosdem.model.api.room
import com.addhen.fosdem.model.api.room2
import com.addhen.fosdem.ui.session.component.FilterRoom
import com.addhen.fosdem.ui.session.component.toFilterRoom
import kotlinx.collections.immutable.toImmutableList

@MultiThemePreviews
@Composable
private fun FilterSessionRoomPreview() {
  var uiState by remember {
    mutableStateOf(
      SearchFilterUiState(
        selectedItems = emptyList<FilterRoom>().toImmutableList(),
        items = listOf(room.toFilterRoom(), room2.toFilterRoom()).toImmutableList(),
      ),
    )
  }
  AppTheme {
    Surface {
      FilterSessionRoomChip(
        searchFilterUiState = uiState,
        onSessionRoomSelected = { sessionType, isSelected ->
          val selectedSessionTypes = uiState.selectedItems.toMutableList()
          val newSelectedSessionTypes = selectedSessionTypes.apply {
            if (isSelected) {
              add(sessionType)
            } else {
              remove(sessionType)
            }
          }
          uiState = uiState.copy(
            selectedItems = newSelectedSessionTypes.toImmutableList(),
            isSelected = newSelectedSessionTypes.isNotEmpty(),
            selectedValues = newSelectedSessionTypes.joinToString { it.name },
          )
        },
        onFilterSessionRoomChipClicked = {},
      )
    }
  }
}
