import io.github.naverz.antonio.buildsrc.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.vanniktech.maven.publish") version "0.29.0"
}

android {
    namespace = "io.github.naverz.antonio.databinding"
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

    buildFeatures {
        dataBinding = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(Android.FRAGMENT)
    implementation(Android.LIFECYCLE_LIVEDATA_KTX)
    implementation(Android.RECYCLER_VIEW)
    implementation(Android.VIEW_PAGER2)
    api(project(path = ":antonio-core"))
    testImplementation(TestDep.JUNIT)
    androidTestImplementation(TestDep.EXT_JUNIT)
    androidTestImplementation(TestDep.ESPRESSO)
}