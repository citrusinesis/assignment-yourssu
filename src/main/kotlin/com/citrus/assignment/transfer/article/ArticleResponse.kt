package com.citrus.assignment.transfer.article

import com.citrus.assignment.transfer.Response

data class ArticleResponse(
    var articleId: Long,
    override var email: String,
    var title: String,
    var content: String,
) : Response(articleId, email)
