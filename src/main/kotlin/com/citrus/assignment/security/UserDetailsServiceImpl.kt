package com.citrus.assignment.security

import com.citrus.assignment.domain.User
import com.citrus.assignment.exception.CustomException
import com.citrus.assignment.exception.ErrorCode
import com.citrus.assignment.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByUsername(username) ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        return UserDetailsImpl(user)
    }

}