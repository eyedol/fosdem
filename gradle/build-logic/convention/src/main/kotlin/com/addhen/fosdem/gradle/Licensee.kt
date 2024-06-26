// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.gradle

import app.cash.licensee.LicenseeExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureLicensee() {
  with(pluginManager) {
    apply("app.cash.licensee")
  }

  configure<LicenseeExtension> {
    allow("Apache-2.0")
    allow("MIT")
    allow("BSD-3-Clause")
    allowUrl("https://developer.android.com/studio/terms.html")
    allowUrl("https://opensource.org/licenses/MIT")
  }
}
