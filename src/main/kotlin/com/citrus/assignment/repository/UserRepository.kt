package com.citrus.assignment.repository

import com.citrus.assignment.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByUsername(username: String): User?

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u set u.refreshToken = :#{#user.refreshToken} where u.id = :#{#user.id}")
    fun updateUserRefreshToken(@Param("user") user: User): User?
}