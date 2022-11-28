package com.citrus.assignment.configuration

import com.citrus.assignment.security.AuthHandlerMethodArgumentResolver
import com.citrus.assignment.security.JwtUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration(
    @Autowired val jwtUtils: JwtUtils
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        super.addArgumentResolvers(resolvers)
        resolvers.add(AuthHandlerMethodArgumentResolver(jwtUtils))
    }
}
