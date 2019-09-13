import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val spekVersion = "2.0.7"
val neo4jJavaDriverVersion = "4.0.0-beta01"
val neo4jVersion = "3.5.9"
val kotlinVersion = plugins.getPlugin(KotlinPluginWrapper::class.java).kotlinPluginVersion
plugins {
  kotlin("jvm") version "1.3.50"
}

group = "neo4k"
version = "0.0.1"

repositories {
  jcenter()
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation("org.neo4j.driver:neo4j-java-driver:$neo4jJavaDriverVersion")

  testImplementation("org.neo4j.test:neo4j-harness:$neo4jVersion")
  testImplementation( "org.assertj:assertj-core:3.6.1")
  testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
  testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
  testRuntimeOnly(kotlin("reflect"))
}

tasks {
  withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
  }
  test {
    useJUnitPlatform {
      includeEngines("spek2")
    }
  }
}
