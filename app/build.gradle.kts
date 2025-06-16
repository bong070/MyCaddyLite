import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.mycaddylite"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mycaddylite"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val properties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            properties.load(localPropertiesFile.inputStream())
            val apiKey = properties["XRAPID_API_KEY"] as String
            buildConfigField("String", "XRAPID_API_KEY", "\"$apiKey\"")
            val mapsApiKey = properties["GOOGLE_MAPS_API_KEY"] as String
            manifestPlaceholders["GOOGLE_MAPS_API_KEY"] = mapsApiKey
        } else {
            throw GradleException("API KEY(S) not found in local.properties")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {
    // Google Maps + Location + retrofit2
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.maps.android:maps-compose:2.11.4")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Compose UI
    implementation("androidx.compose.ui:ui:1.6.6")
    implementation("androidx.compose.ui:ui-graphics:1.6.6")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.6")
    implementation("androidx.compose.material:material:1.6.6")
    implementation("androidx.compose.foundation:foundation:1.6.6")

    // Wear Compose
    implementation("androidx.wear.compose:compose-material:1.2.1")
    implementation("androidx.compose.material3:material3:1.2.1")

    // Jetpack
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("androidx.compose.compiler:compiler:1.5.13")
    implementation("androidx.navigation:navigation-compose:2.7.3")

    // Wearable API
    implementation("com.google.android.gms:play-services-wearable:18.0.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // Test
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.6")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.6")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.6")
}
