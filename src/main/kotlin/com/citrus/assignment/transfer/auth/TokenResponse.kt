package com.citrus.assignment.transfer.auth

import com.citrus.assignment.security.TokenSet

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
) {
    constructor(tokenSet: TokenSet) : this(
        tokenSet.getValue("accessToken"),
        tokenSet.getValue("refreshToken"),
    )
}