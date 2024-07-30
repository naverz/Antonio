import io.github.naverz.antonio.buildsrc.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp") version "1.9.25-1.0.20"
}

android {
    namespace = "io.github.naverz.antonio"
    compileSdk = COMPILE_SDK

    buildFeatures {
        dataBinding = true
    }
    defaultConfig {
        applicationId = "io.github.naverz.antonio"
        minSdk = 16
        targetSdk = TARGET_SDK
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            signingConfig=signingConfigs.getByName("debug")
        }
        debug {
            isMinifyEnabled = false
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
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

//    kaptDebug(project(path = ":antonio-compiler"))
//    kaptRelease(AntonioAnnotation.COMPILER)
    kspDebug(project(path = ":antonio-compiler"))
    kspRelease(AntonioAnnotation.COMPILER)
    releaseImplementation(AntonioAnnotation.ANNOTATION)
    debugImplementation(project(path = ":antonio-annotation"))
    releaseImplementation(Antonio.DATA_BINDING)
    debugImplementation(project(path = ":antonio-databinding"))

    // For paging3
    implementation(Android.PAGING3)
    releaseImplementation(Antonio.DATA_BINDING_PAGING3)
    debugImplementation(project(path = ":antonio-databinding-paging3"))

    implementation(Android.CORE_KTX)
    implementation(Android.APP_COMPAT)
    implementation(Android.MATERIAL)
    implementation(Android.ACTIVITY_KTX)
    implementation(Android.FRAGMENT)
    implementation(Android.CONSTRAINT_LAYOUT)
    implementation(Android.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(Android.LIFECYCLE_LIVEDATA_KTX)
    implementation(Android.VIEW_PAGER2)
    implementation(Android.RECYCLER_VIEW)
    implementation(Android.FLEX_BOX)

    testImplementation(TestDep.JUNIT)
    androidTestImplementation(TestDep.EXT_JUNIT)
    androidTestImplementation(TestDep.ESPRESSO)
    implementation(Nav.FRAGMENT_KTX)
    implementation(Nav.UI_KTX)
}