plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.5.31"
    application
}

group = "ch.toyaxo"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation("dev.kord:kord-core:0.14.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(19)
}

application {
    mainClass.set("ch.toyaxo.mcDiscordBot.MainKt")
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "ch.toyaxo.mcDiscordBot.MainKt")
    }
}
tasks.shadowJar {
    archiveBaseName.set("McDiscordBot")
    archiveClassifier.set("")
    archiveVersion.set("")
    mergeServiceFiles()
}