package com.citrus.assignment.transfer.user

data class UserRequest(
    var email: String,
    var password: String,
    var username: String,
)