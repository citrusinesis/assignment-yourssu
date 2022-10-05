package com.citrus.assignment.service

import com.citrus.assignment.domain.Article
import com.citrus.assignment.domain.User
import com.citrus.assignment.repository.ArticleRepository
import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.transfer.article.ArticleRequset
import com.citrus.assignment.transfer.article.ArticleResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArticleService(
    @Autowired var userRepository: UserRepository,
    @Autowired var articleRepository: ArticleRepository
) {
    //TODO: Seperate validating logic
    val nullish = setOf("", " ", null)
    private fun validateUser(email: String, password: String): User {
        //TODO: Exception Handling
        val user: User = userRepository.findByEmail(email) ?: throw Exception() // UserNotFound
        if (user.password != password) throw Exception() // IncorrectPassword
        return user
    }

    fun create(article: ArticleRequset): ArticleResponse {
        val user: User = validateUser(article.email, article.password)

        if (article.title in nullish
            || article.content in nullish
        ) throw Exception()

        val result: Article = articleRepository.save(
            Article(
                title = article.title,
                content = article.content,
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

    fun modify(articleId: Long, article: ArticleRequset): ArticleResponse {
        val user: User = validateUser(article.email, article.password)
        articleRepository.findById(articleId).get() // If article not found, raise Exception

        if (article.title in nullish
            || article.content in nullish
        ) throw Exception()

        val result: Article = articleRepository.save(
            Article(
                id = articleId,
                title = article.title,
                content = article.content,
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

    //TODO: Implement Delete Service
    fun delete() {}
}