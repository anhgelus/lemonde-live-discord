plugins {
    kotlin("jvm") version "2.0.0"
}

group = "world.anhgelus.lemondelivediscord"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("net.dv8tion:JDA:5.0.0-beta.24") {
        exclude(module="opus-java")
    }
    implementation("org.jsoup:jsoup:1.17.2")
    implementation("net.peanuuutz.tomlkt:tomlkt:0.3.7")
}

tasks.test {
    useJUnitPlatform()
}