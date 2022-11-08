package com.citrus.assignment.security

import com.citrus.assignment.exception.CustomException
import com.citrus.assignment.exception.ErrorCode
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
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
    val experationTime: Long = 1000L * 60

    private fun getAllClaims(token: String): Claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body

    fun <T> getClaimFromToken(token: String, claimesResolver: (Claims) -> T): T =
        claimesResolver.invoke(getAllClaims(token))

    fun getUsernameFromToken(token: String): String = getClaimFromToken(token, Claims::getId)

    fun getExperationDateFromToken(token: String): Date = getClaimFromToken(token, Claims::getExpiration)

    fun generateAccessToken(id: String): String = generateAccessToken(id, mapOf())

    fun generateAccessToken(id: String, claims: Map<String, Any>): String = doGenerateAccessToken(id, claims)

    private fun doGenerateAccessToken(id: String, claims: Map<String, Any>): String =
        Jwts.builder()
            .setClaims(claims)
            .setId(id)
            .setExpiration(Date(System.currentTimeMillis() + experationTime))
            .setIssuedAt(Date(System.currentTimeMillis()))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()

    fun generateRefreshToken(id: String): String = doGenerateRefreshToken(id)

    private fun doGenerateRefreshToken(id: String): String =
        Jwts.builder()
            .setId(id)
            .setExpiration(Date(System.currentTimeMillis() + experationTime * 5))
            .setIssuedAt(Date(System.currentTimeMillis()))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()

    fun generateTokenSet(id: String): TokenSet = generateTokenSet(id, mapOf())

    fun generateTokenSet(id: String, claims: Map<String, Any>): TokenSet = doGenerateTokenSet(id, claims)

    private fun doGenerateTokenSet(id: String, claims: Map<String, Any>): TokenSet = mapOf(
        "accessToken" to Jwts.builder()
            .setClaims(claims)
            .setId(id)
            .setExpiration(Date(System.currentTimeMillis() + experationTime))
            .setIssuedAt(Date(System.currentTimeMillis()))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact(),

        "refreshToken" to Jwts.builder()
            .setId(id)
            .setExpiration(Date(System.currentTimeMillis() + experationTime * 5))
            .setIssuedAt(Date(System.currentTimeMillis()))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact(),
    )

    fun validation(token: String): Boolean = try {
        Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
        true
    } catch (error: Exception) {
        throw CustomException(ErrorCode.INVALID_TOKEN)
    }

    fun getAuthentication(email: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(email)
        return UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.authorities
        )
    }
}

typealias TokenSet = Map<String, String>