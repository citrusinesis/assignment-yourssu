package com.citrus.assignment.transfer.admin

import com.citrus.assignment.domain.Role
import java.time.LocalDateTime

data class ShowResponse(
    var id: Long,
    var email: String,
    var username: String,
    var role: Role,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
)
