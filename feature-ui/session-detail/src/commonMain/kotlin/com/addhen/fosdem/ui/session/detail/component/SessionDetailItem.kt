// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.model.api.Event

data class SessionDetailItemSectionUiState(
  val event: Event,
)

@Composable
internal fun SessionDetailItem(
  uiState: SessionDetailItemSectionUiState,
  listState: LazyListState,
  contentPadding: PaddingValues,
  onLinkClick: (String) -> Unit,
  modifier: Modifier = Modifier,
) {
  val appStrings = LocalStrings.current

  LazyColumn(
    state = listState,
    modifier = modifier,
    contentPadding = contentPadding,
  ) {
    item {
      SessionDetailSummaryCard(
        dateTitle = appStrings.dateTitle,
        roomTitle = appStrings.roomTitle,
        trackTitle = appStrings.trackTitle,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
        sessionItem = uiState.event,
      )
    }

    item {
      SessionDetailContent(
        uiState = uiState,
        onLinkClick = onLinkClick,
      )
    }
  }
}
