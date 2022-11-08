package com.citrus.assignment.security


import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(private val jwtUtils: JwtUtils) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token: String = request.getHeader("Authorization").substring("Bearer ".length)

        if (jwtUtils.validation(token))
            SecurityContextHolder.getContext().authentication =
                jwtUtils.getAuthentication(jwtUtils.getUsernameFromToken(token))

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean = "/user" == request.requestURI
}