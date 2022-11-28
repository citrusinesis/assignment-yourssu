package com.citrus.assignment.service

import com.citrus.assignment.domain.User
import com.citrus.assignment.exception.CustomException
import com.citrus.assignment.exception.ErrorCode
import com.citrus.assignment.repository.ArticleRepository
import com.citrus.assignment.repository.CommentRepository
import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.security.JwtUtils
import com.citrus.assignment.security.TokenSet
import com.citrus.assignment.transfer.auth.AuthInfo
import com.citrus.assignment.transfer.auth.TokenResponse
import com.citrus.assignment.transfer.user.LoginRequest
import com.citrus.assignment.transfer.user.LoginResponse
import com.citrus.assignment.transfer.user.UserRequest
import com.citrus.assignment.transfer.user.UserResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class UserService(
    @Autowired val userRepository: UserRepository,
    @Autowired val articleRepository: ArticleRepository,
    @Autowired val commentRepository: CommentRepository,
    @Autowired val passwordEncoder: PasswordEncoder,
    @Autowired val jwtUtils: JwtUtils,
) : GlobalService(userRepository, articleRepository, commentRepository, passwordEncoder) {
    fun create(userRequest: UserRequest): UserResponse {
        validateEmail(userRequest.email)
        validateDuplication(userRequest)

        val result: User =
            userRepository.save(User(userRequest, passwordEncoder.encode(userRequest.password)))

        return UserResponse(result)
    }

    fun login(loginRequest: LoginRequest): LoginResponse {
        validateEmail(loginRequest.email)
        val user: User = validateUser(loginRequest)
        val tokenSet: TokenSet =
            jwtUtils.generateTokenSet(user.email, mapOf("role" to user.role.toString()))
        user.refreshToken = tokenSet.getValue("refreshToken")
        updateRefreshToken(user)

        val result: User = validateUserWithEmail(user.email)

        return LoginResponse(result, tokenSet.getValue("accessToken"))
    }

    fun delete(authInfo: AuthInfo): HttpStatus {
        val user: User = validateUserWithEmail(authInfo.email)

        commentRepository.deleteAll(commentRepository.findAllByUser(user))
        articleRepository.deleteAll(articleRepository.findAllByUser(user))
        userRepository.delete(user)

        return HttpStatus.OK
    }

    fun refresh(request: HttpServletRequest, authInfo: AuthInfo): TokenResponse {
        val user: User = validateUserWithEmail(authInfo.email)

        val token: String = request.getHeader("Authentication")
            ?: throw CustomException(ErrorCode.GET_HEADER_CONFLICT)

        val tokenSet: TokenSet = jwtUtils.regenerateTokenSet(token)
        user.refreshToken = tokenSet.getValue("refreshToken")
        updateRefreshToken(user)

        return TokenResponse(tokenSet)
    }
}
