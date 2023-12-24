package com.anne.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(scanBasePackages = ["com.anne"])
@EnableScheduling
@ConfigurationPropertiesScan
class BatchApplication

fun main(args: Array<String>) {
    runApplication<BatchApplication>(*args)
}
