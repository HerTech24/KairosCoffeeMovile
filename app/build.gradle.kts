plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.21-1.0.28"
}

android {
    namespace = "com.android.kairoscoffeemovile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.kairoscoffeemovile"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // ✅ PLACEHOLDERS Auth0 (NO incluir applicationId)
        manifestPlaceholders.putAll(
            mapOf(
                "auth0Scheme" to "demo",
                "auth0Domain" to "dev-zjxrrfutupruimyb.us.auth0.com"
            )
        )
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
}

dependencies {

    // 🔷 COMPOSE
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.navigation.compose)

    // 🔷 RETROFIT
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // 🔷 OKHTTP
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // 🔷 COROUTINES
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // 🔷 DATASTORE
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // 🔥 AUTH0 Android SDK
    implementation("com.auth0.android:auth0:2.8.1")

    // 🔷 DEBUG + TEST
    debugImplementation(libs.androidx.ui.tooling)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    // ROOM DATABASE
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
}
