package com.citrus.assignment.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    @Autowired private val jwtUtils: JwtUtils,
) {
    private val filteredURL: Array<String> = arrayOf("/article/**", "/comment/**")

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors().disable()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .headers().frameOptions().disable()
        return http.build()
    }
}