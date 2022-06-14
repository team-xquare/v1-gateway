package com.xquare.gateway.apigateway.configuration.exceptions

class InternalServerError private constructor(
    errorMessage: String
) : BaseException(errorMessage, 500) {
    companion object {
        @JvmField
        val UNCAUGHT_EXCEPTION_OCCURRED_EXCEPTION = InternalServerError("Uncaught Exception Occurred")
    }
}
