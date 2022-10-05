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
    val setOfNullish = setOf("", " ", null)

    //TODO: Seperate validating logic
    private fun validateUser(email: String, password: String): User {
        //TODO: Exception Handling
        val user: User = userRepository.findByEmail(email) ?: throw Exception() // UserNotFound
        if (user.password != password) throw Exception() // IncorrectPassword
        return user
    }

    fun create(article: ArticleRequset): ArticleResponse {
        val user: User = validateUser(article.email, article.password)

        if (article.title in setOfNullish
            || article.content in setOfNullish
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

    //TODO: Implement Modify Service
    fun modify(articleId: String, article: ArticleRequset) {
        val user: User = validateUser(article.email, article.password)

    }

    //TODO: Implement Delete Service
    fun delete() {}
}