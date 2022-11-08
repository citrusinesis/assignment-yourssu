package com.citrus.assignment.service

import com.citrus.assignment.domain.User
import com.citrus.assignment.exception.CustomException
import com.citrus.assignment.exception.ErrorCode
import com.citrus.assignment.repository.ArticleRepository
import com.citrus.assignment.repository.CommentRepository
import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.security.JwtUtils
import com.citrus.assignment.security.TokenSet
import com.citrus.assignment.transfer.DeleteRequest
import com.citrus.assignment.transfer.user.LoginRequest
import com.citrus.assignment.transfer.user.LoginResponse
import com.citrus.assignment.transfer.user.UserRequest
import com.citrus.assignment.transfer.user.UserResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired var userRepository: UserRepository,
    @Autowired var articleRepository: ArticleRepository,
    @Autowired var commentRepository: CommentRepository,
    @Autowired var passwordEncoder: PasswordEncoder,
    @Autowired var jwtUtils: JwtUtils,
) : GlobalService(userRepository, articleRepository, commentRepository, passwordEncoder) {
    fun create(userRequest: UserRequest): UserResponse {
        validateEmail(userRequest.email)
        validateDuplication(userRequest)

        val result: User = userRepository.save(
            User(
                email = userRequest.email,
                password = passwordEncoder.encode(userRequest.password),
                username = userRequest.username,
                refreshToken = "",
                role = userRequest.role,
            )
        )

        return UserResponse(
            email = result.email,
            username = result.username,
            role = result.role
        )
    }

    fun login(loginRequest: LoginRequest): LoginResponse {
        validateEmail(loginRequest.email)
        val user: User = validateUser(loginRequest)
        val tokenSet: TokenSet =
            jwtUtils.generateTokenSet(user.email, mapOf("role" to user.role.toString()))
        user.refreshToken = tokenSet.getValue("refreshToken")

        val result: User =
            userRepository.updateUserRefreshToken(user) ?: throw CustomException(ErrorCode.DB_UPDATE_ERROR)
        
        return LoginResponse(
            email = result.email,
            username = result.username,
            role = result.role,
            accessToken = tokenSet.getValue("accessToken"),
            refreshToken = result.refreshToken,
        )
    }

    fun delete(userInfo: DeleteRequest): HttpStatus {
        val user: User = validateUser(userInfo)

        commentRepository.deleteAll(commentRepository.findAllByUser(user))
        articleRepository.deleteAll(articleRepository.findAllByUser(user))
        userRepository.delete(user)

        return HttpStatus.OK
    }
}