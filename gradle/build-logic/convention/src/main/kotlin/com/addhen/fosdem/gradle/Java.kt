// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.fosdem.gradle

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure

fun Project.configureJava() {
  java {
    toolchain {
      languageVersion.set(JavaLanguageVersion.of(17))
    }
  }
}

private fun Project.java(
  action: JavaPluginExtension.() -> Unit,
) = extensions.configure<JavaPluginExtension>(action)
