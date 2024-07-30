import io.github.naverz.antonio.buildsrc.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.vanniktech.maven.publish") version "0.29.0"
}

android {
    namespace = "io.github.naverz.antonio.core.paging2"
    compileSdk = COMPILE_SDK

    defaultConfig {
        minSdk = 16

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }
}

dependencies {
    implementation(Android.PAGING2)
    implementation(project(path = ":antonio-core"))
    testImplementation(TestDep.JUNIT)
    androidTestImplementation(TestDep.EXT_JUNIT)
    androidTestImplementation(TestDep.ESPRESSO)
}