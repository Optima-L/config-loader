plugins {
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.serialization") version "2.1.21"
    `java-library`
    id("com.gradleup.shadow") version "9.0.0-beta4"
    id("maven-publish")
}

group = "org.optima"
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
    enabled = false
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveBaseName.set("config-loader")
    archiveClassifier.set("")
    archiveVersion.set("")

    from(sourceSets.main.get().output)

    relocate("com.charleskorn.kaml", "org.optima.configloader.shadow.kaml")

    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.named("shadowJar")) {
                classifier = null
            }
            groupId = "com.github.Optima-L"
            artifactId = "config-loader"
            version = "1.0.6"
        }
    }
}

tasks.build {
    dependsOn(tasks.named("shadowJar"))
}
