plugins {
    id("java-library")
}
apply(from = "../gradle/release/release-java-lib.gradle")

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}