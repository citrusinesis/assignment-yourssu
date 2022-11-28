package com.citrus.assignment.transfer.user

import com.citrus.assignment.domain.Role
import com.citrus.assignment.domain.User

data class UserResponse(
    var email: String,
    var username: String,
    var role: Role,
) {
    constructor(user: User) : this(
        email = user.email,
        username = user.username,
        role = user.role
    )
}
