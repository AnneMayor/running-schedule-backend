import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

val bootJar: BootJar by tasks
bootJar.enabled = false

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.allopen")
    kotlin("jvm")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

val testContainersVersion: String by rootProject
val kotestVersion: String by rootProject

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")

    group = "com.anne.running-schedule"
    version = "1.0.0-SNAPSHOT"

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
        testImplementation("org.testcontainers:postgresql:$testContainersVersion")
        testImplementation("org.testcontainers:junit-jupiter:$testContainersVersion")
        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    }
}
