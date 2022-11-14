package com.citrus.assignment.security


import com.citrus.assignment.exception.CustomException
import com.citrus.assignment.exception.ErrorCode
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(private val jwtUtils: JwtUtils) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token: String = request.getHeader("Authorization")
            ?: throw CustomException(ErrorCode.INVALID_TOKEN)

        SecurityContextHolder.getContext().authentication = jwtUtils.getAuthentication(token)
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean =
        Regex("^/(user)(?!(/delete))").containsMatchIn(request.requestURI)
}