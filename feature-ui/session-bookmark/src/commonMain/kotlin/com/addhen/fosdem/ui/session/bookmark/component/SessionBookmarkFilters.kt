// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.addhen.fosdem.ui.session.component.saturdayTab
import com.addhen.fosdem.ui.session.component.sundayTab

const val BookmarkFilterChipDay1TestTag = "BookmarkFilterChipDay1TestTag"
const val BookmarkFilterChipDay2TestTag = "BookmarkFilterChipDay2TestTag"

@Composable
fun BookmarkFilters(
  isDayFirst: Boolean,
  isDaySecond: Boolean,
  onDayFirstChipClick: () -> Unit,
  onDaySecondChipClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier
      .horizontalScroll(rememberScrollState())
      .padding(horizontal = 16.dp),
  ) {
    BookmarkFilterChip(
      labelText = saturdayTab.title,
      isSelected = isDayFirst,
      onClick = onDayFirstChipClick,
      modifier = Modifier.testTag(BookmarkFilterChipDay1TestTag),
    )
    Spacer(modifier = Modifier.size(8.dp))
    BookmarkFilterChip(
      labelText = sundayTab.title,
      isSelected = isDaySecond,
      onClick = onDaySecondChipClick,
      modifier = Modifier.testTag(BookmarkFilterChipDay2TestTag),
    )
  }
}

@Composable
private fun BookmarkFilterChip(
  labelText: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val selectedChipColor = FilterChipDefaults.filterChipColors(
    containerColor = MaterialTheme.colorScheme.secondaryContainer,
    labelColor = MaterialTheme.colorScheme.onSecondaryContainer,
  )
  val selectedChipBoarderColor = FilterChipDefaults.filterChipBorder(
    borderColor = MaterialTheme.colorScheme.outline,
    borderWidth = 0.dp,
    selected = isSelected,
    enabled = true,
  )
  FilterChip(
    onClick = onClick,
    label = {
      ChipInnerText(labelText)
    },
    modifier = modifier,
    leadingIcon = {
      if (isSelected) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = null,
          modifier = Modifier.size(18.dp),
        )
      }
    },
    colors = if (isSelected) {
      selectedChipColor
    } else {
      FilterChipDefaults.filterChipColors()
    },
    border = if (isSelected) {
      selectedChipBoarderColor
    } else {
      FilterChipDefaults.filterChipBorder(
        selected = isSelected,
        enabled = true,
      )
    },
    selected = isSelected,
  )
}

@Composable
private fun ChipInnerText(name: String) {
  Text(
    text = name,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
  )
}
