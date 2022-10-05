package com.citrus.assignment.transfer.comment

data class CommentResponse(
    var commentId: Long,
    var email: String,
    var content: String,
)
