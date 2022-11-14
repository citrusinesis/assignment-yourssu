package com.citrus.assignment.service

import com.citrus.assignment.domain.Article
import com.citrus.assignment.domain.User
import com.citrus.assignment.repository.ArticleRepository
import com.citrus.assignment.repository.CommentRepository
import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.transfer.article.ArticleRequest
import com.citrus.assignment.transfer.article.ArticleResponse
import com.citrus.assignment.transfer.auth.AuthInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class ArticleService(
    @Autowired var userRepository: UserRepository,
    @Autowired var articleRepository: ArticleRepository,
    @Autowired var commentRepository: CommentRepository,
    @Autowired var passwordEncoder: PasswordEncoder
) : GlobalService(userRepository, articleRepository, commentRepository, passwordEncoder) {
    fun create(authInfo: AuthInfo, articleRequest: ArticleRequest): ArticleResponse {
        val user: User = validateUserWithEmail(authInfo.email)
        validateNullish(articleRequest.title, articleRequest.content)

        val result: Article = articleRepository.save(
            Article(
                title = articleRequest.title,
                content = articleRequest.content,
                user = user,
            )
        )

        return ArticleResponse(
            articleId = result.id!!,
            email = result.user.email,
            title = result.title,
            content = result.content
        )
    }

    fun modify(authInfo: AuthInfo, articleId: Long, articleRequest: ArticleRequest): ArticleResponse {
        val user: User = validateUserWithEmail(authInfo.email)
        val article: Article = validateArticle(articleId)
        validateNullish(articleRequest.title, articleRequest.content)
        validateAuthor(user, article.user)

        val result: Article = articleRepository.save(
            Article(
                id = articleId,
                title = articleRequest.title,
                content = articleRequest.content,
                user = user,
            )
        )

        return ArticleResponse(
            articleId = result.id!!,
            email = result.user.email,
            title = result.title,
            content = result.content
        )
    }

    fun delete(authInfo: AuthInfo, articleId: Long): HttpStatus {
        val user: User = validateUserWithEmail(authInfo.email)
        val article: Article = validateArticle(articleId)
        validateAuthor(user, article.user)

        commentRepository.deleteAll(commentRepository.findAllByArticle(article))
        articleRepository.delete(article)

        return HttpStatus.OK
    }
}