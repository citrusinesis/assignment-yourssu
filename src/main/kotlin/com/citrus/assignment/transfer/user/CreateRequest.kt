package com.citrus.assignment.transfer.user

data class CreateRequest(
    var email: String,
    var password: String,
    var username: String,
)