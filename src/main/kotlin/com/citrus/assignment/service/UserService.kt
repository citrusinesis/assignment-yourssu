package com.citrus.assignment.service

import com.citrus.assignment.domain.User
import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.transfer.user.UserRequest
import com.citrus.assignment.transfer.user.UserResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired var userRepository: UserRepository
) {
    fun create(user: UserRequest): UserResponse {
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

    //TODO: Implement Delete Service
    fun delete() {}
}