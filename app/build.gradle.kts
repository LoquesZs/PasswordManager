@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.serialization)
    // id("by.loqueszs.bundle_release_files")
}

android {
    namespace = "by.loqueszs.passwordmanager"
    compileSdk = 33

    defaultConfig {
        applicationId = "by.loqueszs.passwordmanager"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        kotlinCompilerExtensionVersion = "1.4.6"
    }

    kotlinOptions {
        jvmTarget = "17"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        correctErrorTypes = true
    }

    sourceSets {
        getByName("androidTest").assets.srcDirs("$projectDir/schemas")
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

//    bundleReleaseFiles {
//        appVersion.set("1")
//        outputDirectory.set(File(".", "build/outputs"))
//    }
}

dependencies {

    // -----Data-----

    // SQLite
    implementation(libs.sqlite)

    // SQLCipher
    implementation(libs.sqlcipher)

    // Room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)

    // DataStore
    implementation(libs.datastore.preferences)
    implementation(libs.datastore.preferences.core)

    // -----DI-----

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Hilt navigation
    implementation(libs.hilt.navigation.compose)

    // -----Core-----

    // Compose for activity
    implementation(libs.activity.compose)

    // Kotlin

    implementation(libs.core.ktx)
    implementation(libs.kotlinx.serialization.json)

    // Lifecycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)

    // -----Compose-----

    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)

    // Material3
    implementation(libs.material3)
    implementation(libs.material)
    implementation(libs.material.icons.extended)

    // Navigation
    implementation(libs.navigation.compose)

    // -----Accompanist-----

    implementation(libs.accompanist.systemuicontroller)

    // Accompanist navigation
    implementation(libs.accompanist.navigation.animation)

    // Biometric
    implementation(libs.androidx.biometric)

    // Security
    implementation(libs.androidx.security.crypto)

    // Splash
    implementation(libs.androidx.core.splashscreen)

    // Leak Canary
    implementation(libs.leakcanary.android)

    // -----Testing-----

//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//
//    androidTestImplementation(platform("androidx.compose:compose-bom:2023.04.00"))
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.2")
//    debugImplementation("androidx.compose.ui:ui-tooling:1.4.2")
//    debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.2")
//
//    androidTestImplementation("com.google.truth:truth:1.1.3")
//
//    androidTestImplementation("androidx.room:room-testing:2.5.1")
//    androidTestImplementation("com.google.dagger:hilt-android-testing:2.45")
//    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.45")
}
