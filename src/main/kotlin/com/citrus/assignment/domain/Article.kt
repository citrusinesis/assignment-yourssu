package com.citrus.assignment.domain

import com.citrus.assignment.transfer.article.ArticleRequest
import javax.persistence.*

@Entity
@Table(name = "article")
class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    val id: Long? = null,

    @Column(length = 255, nullable = false)
    var content: String,

    @Column(length = 255, nullable = false)
    var title: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,
) : AuditedEntity() {
    constructor(articleRequest: ArticleRequest, user: User) : this(
        title = articleRequest.title,
        content = articleRequest.content,
        user = user,
    )

    constructor(articleId: Long, articleRequest: ArticleRequest, user: User) : this(
        id = articleId,
        title = articleRequest.title,
        content = articleRequest.content,
        user = user,
    )
}
