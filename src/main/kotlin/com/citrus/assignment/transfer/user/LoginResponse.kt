package com.citrus.assignment.transfer.user

import com.citrus.assignment.domain.Role
import com.citrus.assignment.domain.User

data class LoginResponse(
    var email: String,
    var username: String,
    var role: Role,
    var accessToken: String,
    var refreshToken: String,
) {
    constructor(user: User, accessTokenString: String) : this(
        email = user.email,
        username = user.username,
        role = user.role,
        accessToken = accessTokenString,
        refreshToken = user.refreshToken
    )
}
