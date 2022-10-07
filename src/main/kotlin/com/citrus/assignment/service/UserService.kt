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
) : GlobalService(userRepository, articleRepository, commentRepository) {
    fun create(userRequest: UserRequest): UserResponse {
        validateDuplication(userRequest)

        val result: User = userRepository.save(
            User(
                email = userRequest.email,
                password = userRequest.password,
                username = userRequest.username
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