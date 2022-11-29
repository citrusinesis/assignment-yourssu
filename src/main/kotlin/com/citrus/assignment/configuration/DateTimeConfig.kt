package com.citrus.assignment.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.Formatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


@Configuration
class DateTimeConfig {
    @Bean
    fun localDateFormatter(): Formatter<LocalDate> {
        return object : Formatter<LocalDate> {
            override fun parse(text: String, locale: Locale): LocalDate {
                return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd", locale))
            }

            override fun print(time: LocalDate, locale: Locale): String {
                return DateTimeFormatter.ISO_DATE.format(time)
            }
        }
    }
}