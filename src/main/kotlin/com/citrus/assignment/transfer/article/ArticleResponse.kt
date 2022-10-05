package com.citrus.assignment.transfer.article

data class ArticleResponse(
    var articleId: Long,
    var email: String,
    var title: String,
    var content: String,
)
