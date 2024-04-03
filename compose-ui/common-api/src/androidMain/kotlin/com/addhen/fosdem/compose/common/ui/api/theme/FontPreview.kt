// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
private fun FontPreview() {
  Column(
    Modifier.background(color = Color.White),
  ) {
    Text("FOSDEM", style = appTypography().displayLarge)
    Text("Display Medium", style = appTypography().displayMedium)
    Text("Display Small", style = appTypography().displaySmall)
    Text("Headline Large", style = appTypography().headlineLarge)
    Text("Headline Medium", style = appTypography().headlineMedium)
    Text("Headline Small", style = appTypography().headlineSmall)
    Text("Title Large", style = appTypography().titleLarge)
    Text("Title Medium", style = appTypography().titleMedium)
    Text("Title Small", style = appTypography().titleSmall)
    Text("Label Large", style = appTypography().labelLarge)
    Text("Label Medium", style = appTypography().labelMedium)
    Text("Label Small", style = appTypography().labelSmall)
    Text("Body Large", style = appTypography().bodyLarge)
    Text("Body Medium", style = appTypography().bodyMedium)
    Text("Body Small", style = appTypography().bodySmall)
  }
}
