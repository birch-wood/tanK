import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val spekVersion = "2.0.7"
val neo4jJavaDriverVersion = "4.0.0"
val junitVersion = "5.5.2"
val neo4jVersion = "3.5.9"
val testcontainersVersion = "1.12.2"
val kotlinVersion = plugins.getPlugin(KotlinPluginWrapper::class.java).kotlinPluginVersion
plugins {
    kotlin("jvm") version "1.3.70"
}

group = "net.emdal.tanK"
version = "0.0.1"

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.neo4j.driver:neo4j-java-driver:$neo4jJavaDriverVersion")
    testImplementation("org.testcontainers:neo4j:$testcontainersVersion")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")
    testImplementation("org.assertj:assertj-core:3.10.0")
    testCompile( "ch.qos.logback:logback-classic:1.2.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}


tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform()
    }
}
