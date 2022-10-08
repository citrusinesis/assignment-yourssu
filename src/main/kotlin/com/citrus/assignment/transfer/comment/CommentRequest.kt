package com.citrus.assignment.transfer.comment

import com.citrus.assignment.transfer.Request

data class CommentRequest(
    override var email: String,
    override var password: String,
    var content: String,
) : Request(email, password)