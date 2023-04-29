

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("by.loqueszs.bundle_release_files")
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    bundleReleaseFiles {
        appVersion.set("1")
        outputDirectory.set(File(".", "build/outputs"))
    }
}

dependencies {

    // -----Data-----

    // SQLite
    implementation("androidx.sqlite:sqlite-ktx:2.3.1")

    // SQLCipher
    implementation("net.zetetic:android-database-sqlcipher:4.5.3")

    // Room
    implementation("androidx.room:room-runtime:2.5.1")
    implementation("androidx.room:room-ktx:2.5.1")
    ksp("androidx.room:room-compiler:2.5.1")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore-preferences-core:1.0.0")

    // -----DI-----

    // Hilt
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-compiler:2.45")

    // Hilt navigation
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // -----Core-----

    // Compose for activity
    implementation("androidx.activity:activity-compose:1.7.1")

    // Kotlin

    implementation("androidx.core:core-ktx:1.10.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    // -----Compose-----

    implementation(platform("androidx.compose:compose-bom:2023.04.00"))
    implementation("androidx.compose.ui:ui:1.4.2")
    implementation("androidx.compose.ui:ui-graphics:1.4.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.2")

    // Material3
    implementation("androidx.compose.material3:material3:1.1.0-rc01")
//    implementation("androidx.compose.material3:material3-window-size-class:1.0.1")
    implementation("androidx.compose.material:material-icons-extended:1.4.2")
    implementation("androidx.compose.material:material:1.4.2")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.5.3")

    // -----Accompanist-----

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.0")

    // Accompanist navigation
    implementation("com.google.accompanist:accompanist-navigation-animation:0.30.0")

    // Biometric
    implementation("androidx.biometric:biometric:1.1.0")

    // Security
    implementation("androidx.security:security-crypto:1.0.0")

    // Splash
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Leak Canary
    implementation("com.squareup.leakcanary:leakcanary-android:2.10")

    // -----Testing-----

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    androidTestImplementation(platform("androidx.compose:compose-bom:2023.04.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.2")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.4.2")

    androidTestImplementation("com.google.truth:truth:1.1.3")

    androidTestImplementation("androidx.room:room-testing:2.5.1")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.45")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.45")
}