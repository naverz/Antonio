import io.github.naverz.antonio.buildsrc.Incap
import io.github.naverz.antonio.buildsrc.Processor


plugins {
    kotlin("jvm")
    id("com.vanniktech.maven.publish") version "0.29.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":antonio-annotation"))
    implementation(Processor.JAVA_POET)

    implementation(Processor.KSP)
    implementation(Processor.KOTLIN_POET)
    implementation(Processor.KOTLIN_POET_KSP)

    compileOnly(Incap.INCAP)
    annotationProcessor(Incap.PROCESSOR)
}