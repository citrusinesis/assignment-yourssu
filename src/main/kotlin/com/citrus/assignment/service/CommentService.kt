package com.citrus.assignment.service

import com.citrus.assignment.domain.Article
import com.citrus.assignment.domain.Comment
import com.citrus.assignment.domain.User
import com.citrus.assignment.repository.ArticleRepository
import com.citrus.assignment.repository.CommentRepository
import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.transfer.DeleteRequest
import com.citrus.assignment.transfer.comment.CommentRequest
import com.citrus.assignment.transfer.comment.CommentResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class CommentService(
    @Autowired var userRepository: UserRepository,
    @Autowired var articleRepository: ArticleRepository,
    @Autowired var commentRepository: CommentRepository
) : GlobalService(userRepository, articleRepository, commentRepository) {
    fun create(articleId: Long, commentRequest: CommentRequest): CommentResponse {
        val user: User = validateUser(commentRequest)
        val article: Article = validateArticle(articleId)
        validateNullish(commentRequest.content)

        val result: Comment = commentRepository.save(
            Comment(
                content = commentRequest.content,
                article = article,
                user = user
            )
        )

        return CommentResponse(
            commentId = result.id!!,
            email = result.user.email,
            content = result.content
        )
    }

    fun modify(articleId: Long, commentId: Long, commentRequest: CommentRequest): CommentResponse {
        val user: User = validateUser(commentRequest)
        val article: Article = validateArticle(articleId)
        val comment: Comment = validateComment(commentId)
        validateNullish(comment.content)
        validateAuthor(user, comment.user)

        val result: Comment = commentRepository.save(
            Comment(
                id = commentId,
                content = comment.content,
                article = article,
                user = user
            )
        )

        return CommentResponse(
            commentId = result.id!!,
            email = result.user.email,
            content = result.content
        )
    }

    fun delete(articleId: Long, commentId: Long, userInfo: DeleteRequest): HttpStatus {
        val user: User = validateUser(userInfo)
        validateArticle(articleId)
        val comment: Comment = validateComment(commentId)
        validateAuthor(user, comment.user)

        commentRepository.delete(comment)

        return HttpStatus.OK
    }
}