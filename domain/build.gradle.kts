import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks
bootJar.enabled = false
jar.enabled = true

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

group = "com.anne"
version = "0.0.1-SNAPSHOT"
val jsoupVersion: String by rootProject
val kotestVersion: String by rootProject
val mapStructVersion: String by rootProject
val hibernateEnversVersion: String by rootProject

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

configurations {
    all {
        exclude("org.apache.logging.log4j", "log4j-slf4j-impl")
    }
}

allOpen {
    annotation("org.springframework.stereotype.Service")
}

kotlin.sourceSets.main {
    setBuildDir("$buildDir")
}

dependencies {
    api(project(":infra-rds"))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.hibernate:hibernate-envers:$hibernateEnversVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.mapstruct:mapstruct:$mapStructVersion")
    implementation("org.jsoup:jsoup:$jsoupVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
