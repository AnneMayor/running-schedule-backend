rootProject.name = "running-schedule-backend"

include("api")
include("batch", "infra-rds")
include("domain")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.plugin.allopen" -> {
                    val kotlinVersion: String by settings
                    useVersion(kotlinVersion)
                }

                "org.jetbrains.kotlin.jvm" -> {
                    val kotlinVersion: String by settings
                    useVersion(kotlinVersion)
                }

                "org.springframework.boot" -> {
                    val springBootVersion: String by settings
                    useVersion(springBootVersion)
                }

                "org.jetbrains.kotlin.kapt" -> {
                    val kotlinVersion: String by settings
                    useVersion(kotlinVersion)
                }

                "io.spring.dependency-management" -> {
                    val springDependencyManagementVersion: String by settings
                    useVersion(springDependencyManagementVersion)
                }

                "org.jlleitschuh.gradle.ktlint-idea" -> {
                    val ktlintVersion: String by settings
                    useVersion(ktlintVersion)
                }
            }
        }
    }
}
