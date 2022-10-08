package com.citrus.assignment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class AssignmentYourssuApplication

fun main(args: Array<String>) {
    runApplication<AssignmentYourssuApplication>(*args)
}
