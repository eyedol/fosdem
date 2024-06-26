// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about

import androidx.compose.runtime.Composable
import com.addhen.fosdem.core.api.ApplicationInfo
import com.addhen.fosdem.core.api.screens.AboutScreen
import com.addhen.fosdem.core.api.screens.LicensesScreen
import com.addhen.fosdem.core.api.screens.SessionsScreen
import com.addhen.fosdem.core.api.screens.UrlScreen
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class AboutUiPresenterFactory(
  private val presenterFactory: (Navigator) -> AboutPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is AboutScreen -> presenterFactory(navigator)
      else -> null
    }
  }
}

@Inject
class AboutPresenter(
  @Assisted private val navigator: Navigator,
  private val applicationInfo: ApplicationInfo,
) : Presenter<AboutUiState> {
  @Composable
  override fun present(): AboutUiState {
    fun eventSink(event: AboutUiEvent) {
      when (event) {
        is AboutUiEvent.GoToAboutItem -> {
          when (val aboutItem = event.aboutItem) {
            is AboutItem.PrivacyPolicy -> {
              navigator.goTo(UrlScreen(aboutItem.url))
            }

            is AboutItem.License -> {
              navigator.goTo(LicensesScreen)
            }
          }
        }

        is AboutUiEvent.GoToLink -> {
          navigator.goTo(UrlScreen(event.url))
        }

        AboutUiEvent.GoToSession -> navigator.goTo(SessionsScreen)
      }
    }

    return AboutUiState(
      versionName = "${applicationInfo.versionName} (${applicationInfo.versionCode})",
      eventSink = ::eventSink,
    )
  }
}
