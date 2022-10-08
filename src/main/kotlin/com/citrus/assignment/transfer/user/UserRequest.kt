package com.citrus.assignment.transfer.user

import com.citrus.assignment.transfer.Request

data class UserRequest(
    override var email: String,
    override var password: String,
    var username: String,
) : Request(email, password)