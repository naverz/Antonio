// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    kotlin("jvm") version io.github.naverz.antonio.buildsrc.KOTLIN_VERSION apply false
}


buildscript {
    dependencies {
        classpath(io.github.naverz.antonio.buildsrc.GradleDependency.GRADLE_PLUGIN)
        classpath(io.github.naverz.antonio.buildsrc.GradleDependency.BUILD_GRADLE)
    }
    repositories {
        google()
        mavenCentral()
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
