package com.citrus.assignment.repository

import com.citrus.assignment.domain.Article
import com.citrus.assignment.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByArticle(article: Article): List<Comment>
}