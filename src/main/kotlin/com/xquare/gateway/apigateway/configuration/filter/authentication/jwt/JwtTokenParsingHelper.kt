package com.xquare.gateway.apigateway.configuration.filter.authentication.jwt

import org.springframework.http.HttpHeaders

object JwtTokenParsingHelper {
    private const val JWT_HEADER_NAME = "Authorization"
    private const val JWT_TOKEN_PREFIX = "Bearer"

    fun HttpHeaders.getAuthorizationFromHeader() =
        this[JWT_HEADER_NAME]?.let {
            val bearerToken = it[0]
            bearerToken
        }

    fun String.removeJwtTokenPrefix() =
        this.replace(JWT_TOKEN_PREFIX, "").trim()
}
