package com.citrus.assignment.service

import com.citrus.assignment.domain.Article
import com.citrus.assignment.domain.Comment
import com.citrus.assignment.domain.User
import com.citrus.assignment.repository.ArticleRepository
import com.citrus.assignment.repository.CommentRepository
import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.transfer.auth.AuthInfo
import com.citrus.assignment.transfer.comment.CommentRequest
import com.citrus.assignment.transfer.comment.CommentResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class CommentService(
    @Autowired val userRepository: UserRepository,
    @Autowired val articleRepository: ArticleRepository,
    @Autowired val commentRepository: CommentRepository,
    @Autowired val passwordEncoder: PasswordEncoder
) : GlobalService(userRepository, articleRepository, commentRepository, passwordEncoder) {
    fun create(authInfo: AuthInfo, articleId: Long, commentRequest: CommentRequest): CommentResponse {
        val user: User = validateUserWithEmail(authInfo.email)
        val article: Article = validateArticle(articleId)
        validateNullish(commentRequest.content)

        val result: Comment = commentRepository.save(
            Comment(
                content = commentRequest.content,
                article = article,
                user = user
            )
        )

        return CommentResponse(result)
    }

    fun modify(authInfo: AuthInfo, articleId: Long, commentId: Long, commentRequest: CommentRequest): CommentResponse {
        val user: User = validateUserWithEmail(authInfo.email)
        val article: Article = validateArticle(articleId)
        val comment: Comment = validateComment(commentId)
        validateNullish(commentRequest.content)
        validateAuthor(user, comment.user)

        val result: Comment = commentRepository.save(
            Comment(
                id = commentId,
                content = commentRequest.content,
                article = article,
                user = user
            )
        )

        return CommentResponse(result)
    }

    fun delete(authInfo: AuthInfo, articleId: Long, commentId: Long): HttpStatus {
        val user: User = validateUserWithEmail(authInfo.email)
        validateArticle(articleId)
        val comment: Comment = validateComment(commentId)
        validateAuthor(user, comment.user)

        commentRepository.delete(comment)

        return HttpStatus.OK
    }
}
