package com.citrus.assignment.transfer.comment

import com.citrus.assignment.transfer.Response

data class CommentResponse(
    var commentId: Long,
    override var email: String,
    var content: String,
) : Response(commentId, email)
