package com.citrus.assignment.transfer.user

import com.citrus.assignment.domain.Role
import com.citrus.assignment.transfer.Request

data class UserRequest(
    override var email: String,
    override var password: String,
    var username: String,
    var role: Role,
) : Request(email, password)