package com.citrus.assignment.transfer

import com.fasterxml.jackson.annotation.JsonIgnore

abstract class Response(
    @JsonIgnore open var id: Long,
    open var email: String,
)
