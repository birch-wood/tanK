import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.50"
}

group = "neo4k"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.neo4j.driver","neo4j-java-driver","4.0.0-beta01")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}