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
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {
    // Google Maps
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.maps.android:maps-compose:2.11.4")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Compose
    implementation("androidx.compose.ui:ui:1.6.6")
    implementation("androidx.compose.ui:ui-graphics:1.6.6")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.6")
    implementation("androidx.compose.material:material:1.6.6")
    implementation("androidx.compose.foundation:foundation:1.6.6")

    // Wear Compose
    implementation("androidx.wear.compose:compose-material:1.2.1")

    // Jetpack Components
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Wearable APIs
    implementation("com.google.android.gms:play-services-wearable:18.0.0")

    // 테스트
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.6")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.6")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.6")
}
