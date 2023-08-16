// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.gradle

import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType
import java.util.Optional

val Project.libs get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun DependencyHandlerScope.implementation(
  artifact: Optional<Provider<MinimalExternalModuleDependency>>,
) {
  add("implementation", artifact.get())
}

fun DependencyHandlerScope.implementationBundle(
  artifact: Optional<Provider<ExternalModuleDependencyBundle>>,
) {
  add("implementation", artifact.get())
}

fun DependencyHandlerScope.debugImplementation(
  artifact: Optional<Provider<MinimalExternalModuleDependency>>,
) {
  add("debugImplementation", artifact.get())
}

fun DependencyHandlerScope.androidTestImplementation(
  artifact: Optional<Provider<MinimalExternalModuleDependency>>,
) {
  add("androidTestImplementation", artifact.get())
}

fun DependencyHandlerScope.testImplementation(
  artifact: Optional<Provider<MinimalExternalModuleDependency>>,
) {
  add("testImplementation", artifact.get())
}

private fun DependencyHandlerScope.api(
  artifact: Optional<Provider<MinimalExternalModuleDependency>>,
) {
  add("api", artifact.get())
}
