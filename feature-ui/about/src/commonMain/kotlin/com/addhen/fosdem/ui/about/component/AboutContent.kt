// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun AboutContentColumn(
  leadingIcon: ImageVector,
  label: String,
  testTag: String,
  onClickAction: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .height(73.dp)
      .testTag(testTag)
      .clickable { onClickAction() },
  ) {
    Row(
      modifier = Modifier
        .height(72.dp)
        .align(
          alignment = Alignment.Start,
        ),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Icon(
        imageVector = leadingIcon,
        contentDescription = null,
        modifier = Modifier
          .padding(
            horizontal = 12.dp,
          ),
      )
      Text(
        text = label,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier
          .fillMaxWidth()
          .padding(
            end = 16.dp,
          ),
      )
    }
    HorizontalDivider(
      thickness = 1.dp,
      color = MaterialTheme.colorScheme.outlineVariant,
    )
  }
}
