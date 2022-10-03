package com.citrus.assignment.domain

import javax.persistence.*

@Entity
@Table(name = "article")
class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    val id: Long?,

    @Column(length = 255, nullable = false)
    var content: String,

    @Column(length = 255, nullable = false)
    var title: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,
) : AuditedEntity()