plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.dynamic.feature) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.ksp) apply false
    alias(libs.plugins.kotlin.serialization) apply false
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
        compileSdkVersion(libs.versions.compileSdk.get().toInt())

        defaultConfig {
            minSdk = libs.versions.minSdk.get().toInt()
            targetSdk = libs.versions.targetSdk.get().toInt()
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildFeatures.compose = true
        composeOptions {
            kotlinCompilerExtensionVersion = libs.versions.composecompiler.get()
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    dependencies {
        "implementation"(project(":app"))

        "implementation"(libs.core.ktx)

        "implementation"(libs.ui)
        "implementation"(libs.material)
        "implementation"(libs.ui.tooling.preview)
        "implementation"(libs.activity.compose)

        "implementation"(libs.kotlinx.coroutines.core)
        "implementation"(libs.kotlinx.coroutines.android)

        "ksp"(libs.kotlin.inject.compiler.ksp)
        "implementation"(libs.kotlin.inject.runtime)

        "testImplementation"(libs.junit)
        "testImplementation"(libs.turbine)
        "testImplementation"(libs.mockk)
        "testImplementation"(libs.kotlinx.coroutines.test)
    }

}
