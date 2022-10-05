package com.citrus.assignment.controller

import com.citrus.assignment.service.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/comment")
class CommentController(
    @Autowired commentService: CommentService
) {
    //TODO: Implement create comment
    @PostMapping("/create/{article_id}")
    fun create(
        @PathVariable(name = "article_id") articleId: String
    ): String = "CREATE"

    //TODO: Implement modify comment
    @PostMapping("/modify/{article_id}/{id}")
    fun modify(
        @PathVariable(name = "article_id") articleId: String,
        @PathVariable(name = "id") id: String
    ): String = "MODIFY: $id"

    //TODO: Implement delete comment
    @PostMapping("/delete/{article_id}/{id}")
    fun delete(
        @PathVariable(name = "article_id") articleId: String,
        @PathVariable(name = "id") id: String
    ): String = "DELETE: $id"
}