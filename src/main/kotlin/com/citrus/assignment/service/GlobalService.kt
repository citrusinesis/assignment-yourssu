package com.citrus.assignment.service

import com.citrus.assignment.domain.Article
import com.citrus.assignment.domain.Comment
import com.citrus.assignment.domain.User
import com.citrus.assignment.exception.CustomException
import com.citrus.assignment.exception.ErrorCode
import com.citrus.assignment.repository.ArticleRepository
import com.citrus.assignment.repository.CommentRepository
import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.transfer.user.UserRequest
import org.springframework.stereotype.Service

@Service
class GlobalService(
    var user: UserRepository,
    var article: ArticleRepository,
    var comment: CommentRepository
) {
    private val nullish = setOf("", " ", null)

    protected fun validateDuplication(userRequest: UserRequest) {
        if (user.findByEmail(userRequest.email) != null) throw CustomException(ErrorCode.DUPLICATED_EMAIL_ADDRESS)
        if (user.findByUsername(userRequest.username) != null) throw CustomException(ErrorCode.DUPLICATED_USERNAME)
    }

    protected fun validateUser(email: String, password: String): User {
        //TODO: Exception Handling
        val user: User = user.findByEmail(email) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        if (user.password != password) throw CustomException(ErrorCode.LOGIN_FAIL)
        return user
    }

    protected fun validateArticle(articleId: Long): Article = article.findById(articleId).get()

    protected fun validateComment(commentID: Long): Comment = comment.findById(commentID).get()

    protected fun validateNullish(vararg target: String) = target.map {
        if (it in nullish)
            throw CustomException(ErrorCode.FIELD_CANNOT_BE_NULL)
    }

    protected fun validateAuthor(requestUser: String, owner: String) {
        if (requestUser != owner) throw CustomException(ErrorCode.USER_NOT_MATCH)
    }
}