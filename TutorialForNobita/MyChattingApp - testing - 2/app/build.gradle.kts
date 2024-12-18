plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
//    id("com.android.application")
    id("com.google.gms.google-services")
//    id("com.android.application")
}

android {
    namespace = "com.example.mychattingapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mychattingapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.animation.android)
    implementation(libs.androidx.animation.core.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.firebase.database)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.firebase.inappmessaging.display)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.messaging)
    implementation(libs.androidx.material3.lint)
    implementation(libs.generativeai)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.fontawesomecompose)
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-firestore-ktx")
    implementation ("com.google.firebase:firebase-messaging:24.1.0")


    implementation ("androidx.core:core-ktx:1.12.0") // For notification compatibility


//    implementation (libs.fontawesomecompose.vtag)

    implementation (libs.material3) // Use the latest version
    implementation (libs.ui) // Check for the latest Compose UI version
    implementation (libs.androidx.navigation.compose)
    implementation (libs.accompanist.navigation.animation)
    kapt(libs.androidx.room.compiler)
    implementation (libs.androidx.ui.vlatestversion)
    implementation (libs.androidx.core.ktx.vlatestversion)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(kotlin("script-runtime"))

    // Accompanist Pager (for swipeable screens)
    implementation (libs.accompanist.pager.v0360)

    // Optional: Accompanist Pager Indicators (for Tab or Dots)
    implementation (libs.accompanist.pager.indicators.v0360)

    // Emoji Picker Dialogue ..........
//    implementation (libs.androidx.emojipicker)

    implementation ("com.google.firebase:firebase-firestore-ktx:25.1.1") // Use the latest version


//    // OkHttp for HTTP requests
//    implementation("com.squareup.okhttp3:okhttp:4.11.0")
//
//    // Add this for JSON parsing (optional, for example using JSONObject)
//    implementation("org.json:json:20210307")





}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
