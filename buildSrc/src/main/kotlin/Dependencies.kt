object Dependencies {

    val timber = "com.jakewharton.timber:timber:4.7.1"

    object GradlePlugin {
        val version = "3.4.0-beta05"
        val android = "com.android.tools.build:gradle:$version"
        val r8 = "com.android.tools:r8:1.3.52"
        val kotlin = Kotlin.version
        val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}"
        val navigationSafeArgs =
            "android.arch.navigation:navigation-safe-args-gradle-plugin:${AndroidX.Navigation.version}"
        val jetifier = "com.android.tools.build.jetifier:jetifier-processor:1.0.0-beta02"
        val crashlytics = "io.fabric.tools:gradle:1.26.1"
    }

    object Kotlin {
        val version = "1.3.20"
        val stdlibJvm = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"

        val coroutinesVersion = "1.1.1"
        val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        val androidCoroutinesDispatcher =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    }

    object AndroidX {
        val jetifier = "com.android.tools.build.jetifier:jetifier-core:1.0.0-beta02"
        val appCompat = "androidx.appcompat:appcompat:1.0.0"
        val recyclerView = "androidx.recyclerview:recyclerview:1.0.0"
        val constraint = "androidx.constraintlayout:constraintlayout:2.0.0-alpha3"
        val design = "com.google.android.material:material:1.1.0-alpha02"
        val coreKtx = "androidx.core:core-ktx:1.0.0-alpha1"
        val preference = "androidx.preference:preference:1.0.0"
        val fragment = "androidx.fragment:fragment:1.1.0-alpha03"

        object Lifecycle {
            val version = "2.0.0"
            val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            val liveData = "androidx.lifecycle:lifecycle-livedata:$version"
            val runtime = "androidx.lifecycle:lifecycle-runtime:$version"
            val compiler = "androidx.lifecycle:lifecycle-compiler:$version"
        }

        object Room {
            val version = "2.1.0-alpha03"
            val compiler = "androidx.room:room-compiler:$version"
            val runtime = "androidx.room:room-runtime:$version"
            val coroutine = "androidx.room:room-coroutines:$version"
        }

        object Navigation {
            val version = "1.0.0-rc02"
            val runtime = "android.arch.navigation:navigation-runtime:$version"
            val runtimeKtx = "android.arch.navigation:navigation-runtime-ktx:$version"
            val ui = "android.arch.navigation:navigation-ui:$version"
            val fragmentKtx = "android.arch.navigation:navigation-fragment-ktx:$version"
            val uiKtx = "android.arch.navigation:navigation-ui-ktx:$version"
        }

        object Work {
            val version = "1.0.0-alpha12"
            val runtime = "android.arch.work:work-runtime:$version"
            val runtimeKtx = "android.arch.work:work-runtime-ktx:$version"
        }
    }

    object Dagger {
        val version = "2.21"
        val core = "com.google.dagger:dagger:$version"
        val android = "com.google.dagger:dagger-android:$version"
        val support = "com.google.dagger:dagger-android-support:$version"
        val compiler = "com.google.dagger:dagger-compiler:$version"
        val processor = "com.google.dagger:dagger-android-processor:$version"
    }

    object Glide {
        val version = "4.9.0"
        val core = "com.github.bumptech.glide:glide:$version"
        val okhttp3 = "com.github.bumptech.glide:okhttp3-integration:$version"
        val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object Databinding {
        val compiler = "androidx.databinding:databinding-compiler:${GradlePlugin.version}"
    }

    object Test {
        val junit = "junit:junit:4.12"
        val testRunner = "androidx.test:runner:1.1.0"
    }
}
