import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

val jar: Jar by tasks
val bootJar: BootJar by tasks
bootJar.enabled = false
jar.enabled = true

plugins {
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	kotlin("jvm")
	kotlin("plugin.spring")
}

group = "com.anne"
version = "0.0.1-SNAPSHOT"
val coroutineVersion: String by rootProject
val jsoupVersion: String by rootProject
val mapStructVersion: String by rootProject
val springBatchTestVersion: String by rootProject

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(project(":domain"))
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-batch")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
	implementation("org.mapstruct:mapstruct:$mapStructVersion")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.jsoup:jsoup:$jsoupVersion")
	testImplementation("org.springframework.batch:spring-batch-test:$springBatchTestVersion")
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
