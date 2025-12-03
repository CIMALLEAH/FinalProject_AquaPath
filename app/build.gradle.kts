plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.aquapath"
    compileSdk = 36

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.aquapath"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    // Keep these - they are using the version catalog correctly
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material) // This is already included
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout) // This is already included
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth) // This is already included
    implementation(libs.firebase.firestore) // This is already included

    // Add these libraries to your libs.versions.toml and use aliases
    // For now, let's just fix the syntax with quotes
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("com.airbnb.android:lottie:6.1.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3") // Use the latest version
    // --- REMOVE ALL OF THESE REDUNDANT LINES ---
    // implementation (com.google.android.material:material:1.9.0)
    // implementation (androidx.constraintlayout:constraintlayout:2.1.4)
    // implementation (com.google.firebase:firebase-auth-ktx:21.3.0)
    // implementation (com.google.firebase:firebase-firestore-ktx:24.7.0)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
