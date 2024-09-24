// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val room_version by extra("2.6.0")

    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
    }
}

plugins {
    kotlin("jvm") version ("1.9.23")
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false

    id("com.google.dagger.hilt.android") version "2.52" apply false
}