package com.citrus.assignment.transfer.user

import com.citrus.assignment.domain.Role

data class LoginResponse(
    var email: String,
    var username: String,
    var role: Role,
    var accessToken: String,
    var refreshToken: String,
)
