package com.citrus.assignment.transfer.admin

import java.time.LocalDate

data class ShowRequest(
    val username: String? = null,
    val email: String? = null,
    val createdAtStart: LocalDate? = null,
    val createdAtEnd: LocalDate? = null,
    val updatedAtStart: LocalDate? = null,
    val updatedAtEnd: LocalDate? = null,
)
