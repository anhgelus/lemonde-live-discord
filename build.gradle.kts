plugins {
    kotlin("jvm") version "1.9.23"
}

group = "world.anhgelus.lemonde-live-discord"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}