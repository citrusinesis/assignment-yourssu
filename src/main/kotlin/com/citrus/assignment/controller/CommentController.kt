package com.citrus.assignment.controller

import com.citrus.assignment.service.CommentService
import com.citrus.assignment.transfer.comment.CommentRequest
import com.citrus.assignment.transfer.comment.CommentResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comment")
class CommentController(
    @Autowired var commentService: CommentService
) {
    //TODO: Implement create comment
    @PostMapping("/create/{article_id}")
    fun create(
        @PathVariable(name = "article_id") articleId: String,
        @RequestBody comment: CommentRequest
    ): CommentResponse = commentService.create(articleId.toLong(), comment)

    //TODO: Implement modify comment
    @PostMapping("/modify/{article_id}/{id}")
    fun modify(
        @PathVariable(name = "article_id") articleId: String,
        @PathVariable(name = "id") id: String,
        @RequestBody comment: CommentRequest
    ): CommentResponse = commentService.modify(articleId.toLong(), id.toLong(), comment)

    //TODO: Implement delete comment
    @PostMapping("/delete/{article_id}/{id}")
    fun delete(
        @PathVariable(name = "article_id") articleId: String,
        @PathVariable(name = "id") id: String
    ): String = "DELETE: $id"
}