package com.citrus.assignment.exception

class CustomException(
    val errorCode: ErrorCode
) : RuntimeException()
