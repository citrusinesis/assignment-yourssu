package com.citrus.assignment.service

import com.citrus.assignment.repository.ArticleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArticleService(
    @Autowired var articleRepository: ArticleRepository
) {
    
}