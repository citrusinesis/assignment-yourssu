package com.citrus.assignment.service

import com.citrus.assignment.domain.Article
import com.citrus.assignment.domain.Comment
import com.citrus.assignment.domain.User
import com.citrus.assignment.repository.ArticleRepository
import com.citrus.assignment.repository.CommentRepository
import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.transfer.comment.CommentRequest
import com.citrus.assignment.transfer.comment.CommentResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService(
    @Autowired var userRepository: UserRepository,
    @Autowired var articleRepository: ArticleRepository,
    @Autowired var commentRepository: CommentRepository
) {
    //TODO: Seperate validating logic
    val nullish = setOf("", " ", null)

    fun validateUser(email: String, password: String): User {
        //TODO: Exception Handling
        val user: User = userRepository.findByEmail(email) ?: throw Exception("User Not Found")
        if (user.password != password) throw Exception("Incorrect Password")
        return user
    }

    fun validateArticle(articleId: Long): Article = articleRepository.findById(articleId).get()
    fun validateComment(commentID: Long): Comment = commentRepository.findById(commentID).get()

    fun create(articleId: Long, comment: CommentRequest): CommentResponse {
        val user: User = validateUser(comment.email, comment.password)
        val article: Article = validateArticle(articleId)

        if (comment.content in nullish) throw Exception("Content cannot be NULLish")

        val result: Comment = commentRepository.save(
            Comment(
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

    fun modify(articleId: Long, commentId: Long, comment: CommentRequest): CommentResponse {
        val user: User = validateUser(comment.email, comment.password)
        val article: Article = validateArticle(articleId)
        validateComment(commentId)

        if (comment.content in nullish) throw Exception("Content cannot be NULLish")
        if (user.email != comment.email) throw Exception("Comment author does not match")

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

    //TODO: Implement Delete Service
    fun delete() {}
}