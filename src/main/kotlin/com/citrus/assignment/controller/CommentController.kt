package com.citrus.assignment.controller

import com.citrus.assignment.security.Auth
import com.citrus.assignment.service.CommentService
import com.citrus.assignment.transfer.auth.AuthInfo
import com.citrus.assignment.transfer.comment.CommentRequest
import com.citrus.assignment.transfer.comment.CommentResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/comment")
class CommentController(
    @Autowired var commentService: CommentService
) {
    @PostMapping("/create/{article_id}")
    fun create(
        @Auth authInfo: AuthInfo,
        @PathVariable(name = "article_id") articleId: String,
        @RequestBody comment: CommentRequest
    ): CommentResponse = commentService.create(authInfo, articleId.toLong(), comment)

    @PostMapping("/modify/{article_id}/{id}")
    fun modify(
        @Auth authInfo: AuthInfo,
        @PathVariable(name = "article_id") articleId: String,
        @PathVariable(name = "id") id: String,
        @RequestBody comment: CommentRequest
    ): CommentResponse = commentService.modify(authInfo, articleId.toLong(), id.toLong(), comment)

    @PostMapping("/delete/{article_id}/{id}")
    fun delete(
        @Auth authInfo: AuthInfo,
        @PathVariable(name = "article_id") articleId: String,
        @PathVariable(name = "id") id: String,
    ): HttpStatus = commentService.delete(authInfo, articleId.toLong(), id.toLong())
}
