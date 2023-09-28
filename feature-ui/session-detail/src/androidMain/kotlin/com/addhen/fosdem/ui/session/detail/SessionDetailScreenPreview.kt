// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.ui.session.detail.component.SessionDetailItemSectionUiState

@MultiThemePreviews
@Composable
fun SessionDetailItemPreview() {
  val sessionDetailUiState = SessionDetailItemSectionUiState(
    event = day1Event,
  )
  val uiState = ScreenDetailScreenUiState.Loaded(
    sessionDetailUiState = sessionDetailUiState,
    viewBookmarkListRequestState = ViewBookmarkListRequestState.Requested,
  )
  AppTheme {
    Surface {
      SessionItemDetailScreen(
        uiState = uiState,
        onNavigationIconClick = { },
        onBookmarkClick = { _, _ -> },
        onLinkClick = {},
        onCalendarRegistrationClick = { _ -> },
        onShareClick = { _ -> },
        snackbarHostState = SnackbarHostState(),
      )
    }
  }
}
