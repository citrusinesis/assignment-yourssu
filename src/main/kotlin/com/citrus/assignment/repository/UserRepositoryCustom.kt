package com.citrus.assignment.repository

import com.citrus.assignment.domain.User
import com.citrus.assignment.transfer.admin.ShowRequest

interface UserRepositoryCustom {
    fun searchUser(showRequest: ShowRequest): List<User>
}
