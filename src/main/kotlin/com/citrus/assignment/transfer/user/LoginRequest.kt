package com.citrus.assignment.transfer.user

import com.citrus.assignment.transfer.Request

data class LoginRequest(
    override var email: String,
    override var password: String
) : Request(email, password)
