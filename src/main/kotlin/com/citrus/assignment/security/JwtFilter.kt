package com.citrus.assignment.security

import com.citrus.assignment.exception.CustomException
import com.citrus.assignment.exception.ErrorCode
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter(private val jwtUtils: JwtUtils) : OncePerRequestFilter() {
    private val whiteList: List<String> = listOf(
        "/user/create",
        "/user/login",
        "^(/api-docs)",
        "^(/swagger-ui)",
        "/swagger-ui.html",
    )

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token: String = request.getHeader("Authorization")
            ?: throw CustomException(ErrorCode.INVALID_TOKEN)

        if (Regex("^(/show)").containsMatchIn(request.requestURI)) {
            if (jwtUtils.getRole(token) != "ADMIN")
                throw CustomException(ErrorCode.NOT_ADMIN)
        }

        SecurityContextHolder.getContext().authentication = jwtUtils.getAuthentication(token)
        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean = whiteList.map {
        Regex(it).containsMatchIn(request.requestURI)
    }.contains(true)
}
