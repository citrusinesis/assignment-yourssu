package com.citrus.assignment.repository

import com.citrus.assignment.domain.Article
import com.citrus.assignment.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findAllByUser(user: User): List<Article>
}
