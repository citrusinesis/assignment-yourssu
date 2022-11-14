package com.citrus.assignment.repository

import com.citrus.assignment.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun findByUsername(username: String): User?

    @Transactional
    @Modifying
    @Query("update User u set u.refreshToken = :refreshToken where u.id = :id")
    fun updateRefreshToken(@Param("refreshToken") refreshToken: String, @Param("id") id: Long?): Int?
}