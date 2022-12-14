package com.citrus.assignment.service

import com.citrus.assignment.domain.Article
import com.citrus.assignment.domain.Comment
import com.citrus.assignment.domain.User
import com.citrus.assignment.exception.CustomException
import com.citrus.assignment.exception.ErrorCode
import com.citrus.assignment.repository.ArticleRepository
import com.citrus.assignment.repository.CommentRepository
import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.transfer.Request
import com.citrus.assignment.transfer.user.UserRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class GlobalService(
    val user: UserRepository,
    val article: ArticleRepository,
    val comment: CommentRepository,
    val encoder: PasswordEncoder
) {
    private val nullish = setOf("", " ", null)

    private fun <T : Any> Optional<T>.toNullable(): T? = this.orElse(null)

    protected fun validateDuplication(userRequest: UserRequest) {
        if (user.findByEmail(userRequest.email) != null) throw CustomException(ErrorCode.DUPLICATED_EMAIL_ADDRESS)
        if (user.findByUsername(userRequest.username) != null) throw CustomException(ErrorCode.DUPLICATED_USERNAME)
    }

    protected fun validateEmail(email: String) {
        val emailRegex = Regex("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
        if (!emailRegex.matches(email)) throw CustomException(ErrorCode.EMAIL_WRONG_FORMAT)
    }

    protected fun validateUser(request: Request): User {
        validateEmail(request.email)
        val user: User = user.findByEmail(request.email) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        if (!encoder.matches(request.password, user.password)) throw CustomException(ErrorCode.LOGIN_FAIL)
        return user
    }

    protected fun validateUserWithEmail(email: String): User = user.findByEmail(email)
        ?: throw CustomException(ErrorCode.USER_NOT_FOUND)

    protected fun validateArticle(articleId: Long): Article =
        article.findById(articleId).toNullable()
            ?: throw CustomException(ErrorCode.ARTICLE_NOT_FOUND)

    protected fun validateComment(commentId: Long): Comment =
        comment.findById(commentId).toNullable()
            ?: throw CustomException(ErrorCode.COMMENT_NOT_FOUND)

    protected fun validateNullish(vararg target: String) = target.map {
        if (it in nullish)
            throw CustomException(ErrorCode.FIELD_CANNOT_BE_NULL)
    }

    protected fun validateAuthor(requestUser: User, owner: User) {
        if (requestUser.id != owner.id) throw CustomException(ErrorCode.USER_NOT_MATCH)
    }

    protected fun updateRefreshToken(requestUser: User) =
        user.updateRefreshToken(requestUser.refreshToken, requestUser.id)
            ?: throw CustomException(ErrorCode.DB_UPDATE_ERROR)
}
