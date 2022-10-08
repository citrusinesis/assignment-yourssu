package com.citrus.assignment.transfer.article

import com.citrus.assignment.transfer.Request

data class ArticleRequest(
    override var email: String,
    override var password: String,
    var title: String,
    var content: String,
) : Request(email, password)