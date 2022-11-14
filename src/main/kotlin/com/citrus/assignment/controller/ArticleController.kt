package com.citrus.assignment.controller


import com.citrus.assignment.service.ArticleService
import com.citrus.assignment.transfer.DeleteRequest
import com.citrus.assignment.transfer.article.ArticleRequest
import com.citrus.assignment.transfer.article.ArticleResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/article")
class ArticleController(
    @Autowired var articleService: ArticleService,
) {
    @PostMapping("/create")
    fun create(@RequestBody article: ArticleRequest): ArticleResponse =
        articleService.create(article)

    @PostMapping("/modify/{id}")
    fun modify(
        @PathVariable(name = "id") id: String,
        @RequestBody article: ArticleRequest,
    ): ArticleResponse = articleService.modify(id.toLong(), article)

    @PostMapping("/delete/{id}")
    fun delete(
        @PathVariable(name = "id") id: String,
        @RequestBody userInfo: DeleteRequest
    ): HttpStatus = articleService.delete(id.toLong(), userInfo)
}