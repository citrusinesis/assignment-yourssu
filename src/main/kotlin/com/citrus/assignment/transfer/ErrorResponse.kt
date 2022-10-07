package com.citrus.assignment.transfer

import java.time.LocalDateTime

data class ErrorResponse(
    var time: LocalDateTime,
    var status: String,
    var message: String,
    var requestURI: String,
)
