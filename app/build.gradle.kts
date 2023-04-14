plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "io.coreflodev.dog"
        minSdk = 21
        targetSdk = 33
        versionCode = 2
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("test.jks")
            storePassword = "testtest"
            keyAlias = "key0"
            keyPassword = "testtest"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs["release"]
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            packagingOptions.setExcludes(setOf("DebugProbesKt.bin"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.5"
    }
    packaging {
        resources {
            excludes += setOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/**",
            )
        }
    }
    namespace = "io.coreflodev.dog"
    file("../feature/").listFiles()?.forEach { sub -> dynamicFeatures += ":feature:" + sub.name }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.0")

    val compose_version = "1.5.0-alpha02"
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation("androidx.activity:activity-compose:1.7.0")

    implementation("androidx.lifecycle:lifecycle-common-java8:2.6.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // Http
    val ktor_version = "2.2.4"
    implementation("io.ktor:ktor-client-json:$ktor_version")
    implementation("io.ktor:ktor-client-serialization-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-client-android:$ktor_version")

    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.9.3"))
    implementation("com.squareup.okhttp3:okhttp")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    implementation("io.coil-kt:coil:2.3.0")
    implementation("io.coil-kt:coil-compose:2.3.0")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.10")

    ksp("me.tatarka.inject:kotlin-inject-compiler-ksp:0.6.1")
    implementation("me.tatarka.inject:kotlin-inject-runtime:0.6.1")

    testImplementation("junit:junit:4.13.2")
    testImplementation("app.cash.turbine:turbine:0.12.3")
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")

    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
}
