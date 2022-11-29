package com.citrus.assignment.repository

import com.citrus.assignment.domain.QUser
import com.citrus.assignment.domain.User
import com.citrus.assignment.transfer.admin.ShowRequest
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.DateTimePath
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime

@Repository
class UserRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : UserRepositoryCustom {
    val user: QUser = QUser.user
    override fun searchUser(showRequest: ShowRequest): List<User> = showRequest.run {
        jpaQueryFactory
            .selectFrom(user)
            .where(
                usernameEq(username),
                emailEq(email),
                createdAtEq(createdAtStart, createdAtEnd),
                updatedAtEq(updatedAtStart, updatedAtEnd)
            )
            .fetch()
    }

    private fun usernameEq(username: String?): BooleanExpression? {
        if (username == null) return null
        return user.username.eq(username)
    }

    private fun emailEq(email: String?): BooleanExpression? {
        if (email == null) return null
        return user.email.eq(email)
    }

    private fun dateEq(
        date: DateTimePath<LocalDateTime>,
        dateStart: LocalDate?,
        dateEnd: LocalDate?,
    ): BooleanExpression? {
        val start: LocalDateTime? = dateStart?.atStartOfDay()
        val end: LocalDateTime? = dateEnd?.atStartOfDay()

        return if (start != null && end != null) {
            date.between(start, end)
        } else if (start != null) {
            date.after(start)
        } else if (end != null) {
            date.before(end)
        } else {
            null
        }
    }

    private fun createdAtEq(
        createdAtStart: LocalDate?,
        createdAtEnd: LocalDate?,
    ): BooleanExpression? = dateEq(user.createdAt, createdAtStart, createdAtEnd)

    private fun updatedAtEq(
        updatedAtStart: LocalDate?,
        updatedAtEnd: LocalDate?,
    ): BooleanExpression? = dateEq(user.updatedAt, updatedAtStart, updatedAtEnd)
}
