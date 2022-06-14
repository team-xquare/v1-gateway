package com.xquare.gateway.apigateway.infrastructure.jwt

interface JwtTokenParser {
    fun parseToken(token: String): Map<String, Any>
}
