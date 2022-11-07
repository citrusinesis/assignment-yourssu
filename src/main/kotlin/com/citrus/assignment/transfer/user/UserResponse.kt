package com.citrus.assignment.transfer.user

import com.citrus.assignment.domain.Role

data class UserResponse(
    var email: String,
    var username: String,
    var role: Role,    
)
