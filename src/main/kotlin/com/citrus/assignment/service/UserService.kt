package com.citrus.assignment.service

import com.citrus.assignment.domain.User
import com.citrus.assignment.repository.ArticleRepository
import com.citrus.assignment.repository.CommentRepository
import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.transfer.DeleteRequest
import com.citrus.assignment.transfer.user.UserRequest
import com.citrus.assignment.transfer.user.UserResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired var userRepository: UserRepository,
    @Autowired var articleRepository: ArticleRepository,
    @Autowired var commentRepository: CommentRepository
) {
    fun validateUser(email: String, password: String): User {
        //TODO: Exception Handling
        val user: User = userRepository.findByEmail(email) ?: throw Exception("User Not Found")
        if (user.password != password) throw Exception("Incorrect Password")
        return user
    }

    fun create(user: UserRequest): UserResponse {
        if (userRepository.findByEmail(user.email) != null) throw Exception("Email has already used")
        if (userRepository.findByUsername(user.username) != null) throw Exception("Username has already taken")

        val result: User = userRepository.save(
            User(
                email = user.email,
                password = user.password,
                username = user.username
            )
        )

        return UserResponse(
            email = result.email,
            username = result.username
        )
    }

    fun delete(userInfo: DeleteRequest): HttpStatus {
        val user: User = validateUser(userInfo.email, userInfo.password)

        commentRepository.deleteAll(commentRepository.findAllByUser(user))
        articleRepository.deleteAll(articleRepository.findAllByUser(user))
        userRepository.delete(user)

        return HttpStatus.OK
    }
}