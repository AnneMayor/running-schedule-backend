rootProject.name = "running-schedule"

include("api")
include("batch" , "infra-rds")
include("domain")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            when(requested.id.id) {
                "org.springframework.boot" -> {
                    val springBootVersion: String by settings
                    useVersion(springBootVersion)
                }

                "io.spring.dependency-management" -> {
                    val springDependencyManagementVersion: String by settings
                    useVersion(springDependencyManagementVersion)
                }
            }
        }
    }
}
