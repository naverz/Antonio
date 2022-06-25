import io.github.naverz.antonio.buildsrc.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}
apply(from = "../gradle/release/release-java-lib.gradle")

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

dependencies {
    implementation(project(":antonio-annotation"))
    implementation(Processor.JAVA_POET)

    implementation(Processor.KSP)
    implementation(Processor.KOTLIN_POET)
    implementation(Processor.KOTLIN_POET_KSP)
}