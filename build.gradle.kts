plugins {
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.serialization") version "2.0.20"
    `java-library`
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kaml)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.serialization)
}

kotlin {
    jvmToolchain(21)
}

tasks.jar {
    archiveBaseName.set("config-loader")
    from(sourceSets.main.get().output)
}