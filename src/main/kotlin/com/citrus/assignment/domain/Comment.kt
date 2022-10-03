package com.citrus.assignment.domain

import javax.persistence.*

@Entity
@Table(name = "comment")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    val id: Long?,

    @Column(length = 255, nullable = false)
    var content: String,

    @ManyToOne
    @JoinColumn(name = "article_id")
    val article: Article,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,
) : AuditedEntity()