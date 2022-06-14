package com.xquare.gateway.apigateway.configuration.filter.authentication.exception

import com.xquare.gateway.apigateway.configuration.exceptions.BaseException

class InvalidJwtException private constructor(
    errorMessage: String
) : BaseException(errorMessage, 401) {
    companion object {
        @JvmField
        val JWT_EXPIRED_EXCEPTION = InvalidJwtException("Jwt Token Expired")
        @JvmField
        val INVALID_JWT_SIGNATURE_EXCEPTION = InvalidJwtException("Invalid Jwt Signature")
    }
}
