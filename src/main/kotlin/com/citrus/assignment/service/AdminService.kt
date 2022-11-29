package com.citrus.assignment.service

import com.citrus.assignment.domain.User
import com.citrus.assignment.exception.CustomException
import com.citrus.assignment.exception.ErrorCode
import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.transfer.admin.ShowRequest
import com.citrus.assignment.transfer.admin.ShowResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdminService(
    @Autowired val userRepository: UserRepository
) {
    private fun List<User>.toShowResponse(): List<ShowResponse> = this
        .map {
            ShowResponse(
                id = it.id ?: throw CustomException(ErrorCode.USER_NOT_FOUND),
                email = it.email,
                username = it.username,
                role = it.role,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
            )
        }

    fun showUser(showRequest: ShowRequest): List<ShowResponse> =
        userRepository.searchUser(showRequest).toShowResponse()
}
