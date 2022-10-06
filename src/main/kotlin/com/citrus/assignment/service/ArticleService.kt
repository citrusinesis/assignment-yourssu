package com.citrus.assignment.service

import com.citrus.assignment.domain.Article
import com.citrus.assignment.domain.User
import com.citrus.assignment.repository.ArticleRepository
import com.citrus.assignment.repository.CommentRepository
import com.citrus.assignment.repository.UserRepository
import com.citrus.assignment.transfer.DeleteRequest
import com.citrus.assignment.transfer.article.ArticleRequest
import com.citrus.assignment.transfer.article.ArticleResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class ArticleService(
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

    fun create(article: ArticleRequest): ArticleResponse {
        val user: User = validateUser(article.email, article.password)

        if (article.title in nullish
            || article.content in nullish
        ) throw Exception("Title or Content cannot be NULLish")

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

    fun modify(articleId: Long, article: ArticleRequest): ArticleResponse {
        val user: User = validateUser(article.email, article.password)
        validateArticle(articleId) // If article not found, raise Exception

        if (article.title in nullish
            || article.content in nullish
        ) throw Exception("Title or Content cannot be NULLish")
        if (user.email != article.email) throw Exception("Article author does not match")

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

    fun delete(articleId: Long, userInfo: DeleteRequest): HttpStatus {
        val user: User = validateUser(userInfo.email, userInfo.password)
        val article: Article = validateArticle(articleId)

        if (user.email != article.user.email) throw Exception("Article author does not match")

        commentRepository.deleteAll(commentRepository.findAllByArticle(article))
        articleRepository.delete(article)

        return HttpStatus.OK
    }
}