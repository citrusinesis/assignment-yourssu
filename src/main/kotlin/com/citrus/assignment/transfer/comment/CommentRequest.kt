package com.citrus.assignment.transfer.comment

data class CommentRequest(
    var email: String,
    var password: String,
    var content: String,
)