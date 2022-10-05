package com.citrus.assignment.transfer.article

data class ArticleRequset(
    var email: String,
    var password: String,
    var title: String,
    var content: String,
)