package com.xquare.gateway.apigateway.configuration.exceptions

class InternalServerError private constructor(
    errorMessage: String
) : BaseException(errorMessage, 500) {
    companion object {
        val UNCAUGHT_EXCEPTION_OCCURRED = InternalServerError("Uncaught Exception Occurred")
    }
}
