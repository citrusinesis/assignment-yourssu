package com.citrus.assignment.transfer

data class ResponseWithError<T : Any>(
    var isError: Boolean,
    var body: T? = null,
    var errDescription: String? = null,
)