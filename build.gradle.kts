plugins {
    kotlin("jvm") version "2.1.21"
    kotlin("plugin.serialization") version "2.1.21"
    `java-library`
    id("com.gradleup.shadow") version "9.0.0-beta4"
}

group = "org.optima"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Используем версии из libs. Если нет, можно указать напрямую.
dependencies {
    implementation("com.charleskorn.kaml:kaml:0.92.0")
    implementation("io.insert-koin:koin-core:4.0.0-RC1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
}

kotlin {
    jvmToolchain(21)
}

tasks.jar {
    archiveBaseName.set("config-loader")
    from(sourceSets.main.get().output)
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveBaseName.set("config-loader")
    archiveClassifier.set("")
    archiveVersion.set("")

    from(sourceSets.main.get().output)

    relocate("com.charleskorn.kaml", "org.optima.configloader.shadow.kaml")

    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
}

tasks.build {
    dependsOn(tasks.named("shadowJar"))
}
