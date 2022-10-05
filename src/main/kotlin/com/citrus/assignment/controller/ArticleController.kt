package com.citrus.assignment.controller


import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.service.ArticleService
import com.citrus.assignment.transfer.article.ArticleRequset
import com.citrus.assignment.transfer.article.ArticleResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/article")
class ArticleController(
    @Autowired var articleService: ArticleService,
    @Autowired var userRepository: UserRepository
) {
    @PostMapping("/create")
    fun create(@RequestBody article: ArticleRequset): ArticleResponse =
        articleService.create(article)

    //TODO: Implement modify article
    @PostMapping("/modify/{id}")
    fun modify(
        @PathVariable(name = "id") id: String,
        @RequestBody article: ArticleRequset,
    ): String = "$id $article"

    //TODO: Implement delete article
    @PostMapping("/delete/{id}")
    fun delete(@PathVariable(name = "id") id: String): String = "DELETE: $id"
}