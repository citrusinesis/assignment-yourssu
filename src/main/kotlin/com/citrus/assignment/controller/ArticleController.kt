package com.citrus.assignment.controller

import com.citrus.assignment.service.ArticleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/article")
class ArticleController(
    @Autowired var articleService: ArticleService
) {
    //TODO: Implement create article
    @PostMapping("/create")
    fun create(): String = "CREATE"

    //TODO: Implement modify article
    @PostMapping("/modify/{id}")
    fun modify(@PathVariable(name = "id") id: String): String = "MODIFY: $id";

    //TODO: Implement delete article
    @PostMapping("/delete/{id}")
    fun delete(@PathVariable(name = "id") id: String): String = "DELETE: $id";
}