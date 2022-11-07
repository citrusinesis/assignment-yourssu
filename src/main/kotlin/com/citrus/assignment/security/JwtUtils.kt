package com.citrus.assignment.security

import com.citrus.assignment.domain.Role
import com.citrus.assignment.domain.User
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
    val experationTime: Long = 1000L * 60 * 3

    fun createToken(user: User): String {
        val claims: Claims = Jwts.claims()
        claims["email"] = user.email
        claims["username"] = user.username
        claims["role"] = user.role.toString()

        return Jwts.builder()
            .setClaims(claims)
            .setExpiration(Date(System.currentTimeMillis() + experationTime))
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact()
    }

    private fun getAllClaims(token: String): Claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body

    fun validation(token: String): Boolean = getAllClaims(token).expiration.after(Date())

    fun parseEmail(token: String) = getAllClaims(token)["email"] as String

    fun parseUsername(token: String) = getAllClaims(token)["username"] as String

    fun parseRole(token: String) = getAllClaims(token)["role"] as Role

    fun getAuthentication(username: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.authorities
        )
    }
}