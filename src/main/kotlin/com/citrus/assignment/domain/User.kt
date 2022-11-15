package com.citrus.assignment.domain

import com.citrus.assignment.transfer.user.UserRequest
import javax.persistence.*

@Entity
@Table(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long? = null,

    @Column(length = 255, nullable = false)
    var email: String,

    @Column(length = 255, nullable = false)
    var password: String,

    @Column(length = 255, nullable = false)
    var username: String,

    @Column(length = 255, nullable = false, name = "refresh_token")
    var refreshToken: String,

    @Column(length = 255, nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role,
) : AuditedEntity() {
    constructor(userRequest: UserRequest, encodedPassword: String) : this(
        email = userRequest.email,
        password = encodedPassword,
        username = userRequest.username,
        refreshToken = "",
        role = userRequest.role,
    )
}