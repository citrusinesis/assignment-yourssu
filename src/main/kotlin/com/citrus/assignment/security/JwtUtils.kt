package com.citrus.assignment.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils(
    private val userDetailsService: UserDetailsServiceImpl,
    @Value("\${spring.jwt.secret}") private val secret: String,
) {
    val expirationTime: Long = 1000L * 60

    fun generateAccessToken(id: String, claims: Map<String, Any> = mapOf()): String = "Bearer " + JWT.create()
        .withJWTId(id)
        .withPayload(claims)
        .withExpiresAt(Date(System.currentTimeMillis() + expirationTime))
        .withIssuedAt(Date(System.currentTimeMillis()))
        .sign(Algorithm.HMAC256(secret))

    fun generateRefreshToken(id: String): String = "Bearer " + JWT.create()
        .withJWTId(id)
        .withExpiresAt(Date(System.currentTimeMillis() + expirationTime))
        .withIssuedAt(Date(System.currentTimeMillis()))
        .sign(Algorithm.HMAC256(secret))

    fun generateTokenSet(id: String, claims: Map<String, Any> = mapOf()): TokenSet = mutableMapOf(
        "accessToken" to generateAccessToken(id, claims),
        "refreshToken" to generateRefreshToken(id),
    )

    private fun verification(token: String): DecodedJWT = JWT.require(Algorithm.HMAC256(secret))
            .acceptExpiresAt(expirationTime)
            .build()
            .verify(token)


    fun getAuthentication(tokenString: String): Authentication {
        val token: DecodedJWT = verification(tokenString)
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(token.id)
        return UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.authorities
        )
    }
}

typealias TokenSet = Map<String, String>