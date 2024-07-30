import io.github.naverz.antonio.buildsrc.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.vanniktech.maven.publish") version "0.29.0"
}

android {
    namespace = "io.github.naverz.antonio.core"
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
    implementation(Android.FRAGMENT)
    implementation(Android.LIFECYCLE_LIVEDATA_KTX)
    implementation(Android.RECYCLER_VIEW)
    implementation(Android.VIEW_PAGER2)
    testImplementation(TestDep.JUNIT)
    androidTestImplementation(TestDep.EXT_JUNIT)
    androidTestImplementation(TestDep.ESPRESSO)
}