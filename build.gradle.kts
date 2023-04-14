plugins {
    val kotlin_version = "1.8.20"
    id("com.android.application") version "8.1.0-alpha11" apply false
    id("com.android.dynamic-feature") version "8.1.0-alpha11" apply false
    id("org.jetbrains.kotlin.android") version kotlin_version apply false
    id("com.google.devtools.ksp") version "$kotlin_version-1.0.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version kotlin_version apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

allprojects {
    pluginManager.withPlugin("com.android.dynamic-feature") {
        apply(plugin = "kotlin-android")
        apply(plugin = "com.google.devtools.ksp")
        configureAndroidProjectFeature()
    }
}

fun Project.configureAndroidProjectFeature() {
    extensions.configure<com.android.build.gradle.BaseExtension> {
        compileSdkVersion(33)

        defaultConfig {
            minSdk = 21
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildFeatures.compose = true
        composeOptions {
            kotlinCompilerExtensionVersion = "1.4.5"
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    dependencies {
        "implementation"(project(":app"))

        "implementation"("androidx.core:core-ktx:1.10.0")

        val compose_version = "1.5.0-alpha02"
        "implementation"("androidx.compose.ui:ui:$compose_version")
        "implementation"("androidx.compose.material:material:$compose_version")
        "implementation"("androidx.compose.ui:ui-tooling-preview:$compose_version")
        "implementation"("androidx.activity:activity-compose:1.7.0")

        "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

        "ksp"("me.tatarka.inject:kotlin-inject-compiler-ksp:0.6.1")
        "implementation"("me.tatarka.inject:kotlin-inject-runtime:0.6.1")

        "testImplementation"("junit:junit:4.13.2")
        "testImplementation"("app.cash.turbine:turbine:0.12.3")
        "testImplementation"("io.mockk:mockk:1.13.4")
        "testImplementation"("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    }

}
