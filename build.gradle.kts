plugins {
    kotlin("jvm") version "1.9.23"
}

group = "stack"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:lincheck:2.26")
    testImplementation("junit:junit:4.13.2")
    implementation("org.jetbrains.kotlinx:atomicfu:0.23.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}