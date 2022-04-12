package com.xquare.gateway.apigateway.configuration.authentication.exception

import com.xquare.gateway.apigateway.configuration.exceptions.BaseException

class InvalidJwtException(
    errorMessage: String
): BaseException(errorMessage, 401) {
    companion object {
        const val JWT_EXPIRED_MESSAGE = "Jwt Token Expired"
    }
}