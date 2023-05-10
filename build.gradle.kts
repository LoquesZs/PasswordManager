// Top-level build file where you can add configuration options common to all sub-projects/modules.
@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.application).apply(false)
    // alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kapt).apply(false)
    alias(libs.plugins.serialization).apply(false)
    alias(libs.plugins.ksp).apply(false)
    alias(libs.plugins.hilt.android).apply(false)
    // alias(libs.plugins.bundleReleaseFiles).apply(false)
}
