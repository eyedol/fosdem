// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews

@MultiThemePreviews
@Composable
private fun SessionBookmarkButtonPreview() {
  AppTheme {
    Surface {
      SessionBookmarkButton(
        eventId = 0,
        isBookmarked = true,
        onBookmarkClick = {},
        expanded = false,
      )
    }
  }
}
