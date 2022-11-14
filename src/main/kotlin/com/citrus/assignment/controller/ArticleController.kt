package com.citrus.assignment.controller


import com.citrus.assignment.security.Auth
import com.citrus.assignment.service.ArticleService
import com.citrus.assignment.transfer.article.ArticleRequest
import com.citrus.assignment.transfer.article.ArticleResponse
import com.citrus.assignment.transfer.auth.AuthInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/article")
class ArticleController(
    @Autowired var articleService: ArticleService,
) {
    @PostMapping("/create")
    fun create(@Auth authInfo: AuthInfo, @RequestBody article: ArticleRequest): ArticleResponse =
        articleService.create(authInfo, article)

    @PostMapping("/modify/{id}")
    fun modify(
        @Auth authInfo: AuthInfo,
        @PathVariable(name = "id") id: String,
        @RequestBody article: ArticleRequest,
    ): ArticleResponse = articleService.modify(authInfo, id.toLong(), article)

    @PostMapping("/delete/{id}")
    fun delete(
        @Auth authInfo: AuthInfo,
        @PathVariable(name = "id") id: String,
    ): HttpStatus = articleService.delete(authInfo, id.toLong())
}