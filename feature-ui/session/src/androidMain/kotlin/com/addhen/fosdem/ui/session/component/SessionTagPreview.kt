package com.addhen.fosdem.ui.session.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.compose.common.ui.api.theme.trackColors

@MultiThemePreviews
@Composable
fun SessionTagPreview() {
  val trackColor = trackColors()
  AppTheme {
    Surface {
      SessionTag(
        label = "Dev Room",
        labelColor = trackColor.name,
        backgroundColor =trackColor.typeCertification,
      )
    }
  }
}
