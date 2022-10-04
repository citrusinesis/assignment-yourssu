package com.citrus.assignment.service

import com.citrus.assignment.domain.User
import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.transfer.user.CreateRequest
import com.citrus.assignment.transfer.user.CreateResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired var userRepository: UserRepository
) {
    fun create(user: CreateRequest): CreateResponse {
        val returnedUser: User = userRepository.save(
            User(
                email = user.email,
                password = user.password,
                username = user.username
            )
        )

        return CreateResponse(
            email = returnedUser.email,
            username = returnedUser.username
        )
    }
}