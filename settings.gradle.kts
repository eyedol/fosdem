// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0


rootProject.name = "fosdem-events-app"

pluginManagement {
  includeBuild("gradle/build-logic")
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }

  resolutionStrategy {
    eachPlugin {
      when (requested.id.id) {
        "deploygate" -> useModule("com.deploygate:gradle:${requested.version}")
      }
    }
  }
}

dependencyResolutionManagement {
  // repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    mavenLocal()

    // Prerelease versions of Compose Multiplatform
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }
}

include(
  ":feature-ui:about",
  ":feature-ui:map",
  ":feature-ui:licenses",
  ":feature-ui:session-search",
  ":feature-ui:session-bookmark",
  ":feature-ui:session-detail",
  ":feature-ui:session-list",
  ":feature-ui:session-component",
  ":feature-ui:main",
  ":compose-ui:common-api",
  ":compose-ui:html",
  ":data:events",
  ":data:events-api",
  ":data:rooms",
  ":data:rooms-api",
  ":data:model-api",
  ":data:licenses",
  ":data:licenses-api",
  ":core-api",
  ":data:core-api",
  ":data:sqldelight",
  ":data:sqldelight-api",
  ":testing",
  ":android-app",
  ":desktop-app",
  ":ios-shared",
)
