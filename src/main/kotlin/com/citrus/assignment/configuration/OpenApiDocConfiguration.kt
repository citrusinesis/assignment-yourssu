package com.citrus.assignment.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiDocConfiguration {
    @Bean
    fun openAPI(@Value("\${springdoc.version}") appVersion: String): OpenAPI {
        val contact: Contact = Contact()
            .name("송지호")
            .email("jihojiho2003@gmail.com")

        val info: Info = Info()
            .title("API Documentation")
            .version(appVersion)
            .description("API Description")
            .termsOfService("https://swagger.io/terms/")
            .contact(contact)

        return OpenAPI()
            .components(Components())
            .info(info)
    }
}
