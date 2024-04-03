// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.ImageVectorResource
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.ui.about.component.AboutDetail

@MultiThemePreviews
@Composable
private fun AboutDetailPreview() {
  AppTheme {
    Surface {
      AboutDetail(
        onLinkClick = {},
        imageResource = ImageVectorResource.AboutBanner,
      )
    }
  }
}
