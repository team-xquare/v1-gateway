package com.xquare.gateway.apigateway.configuration.exceptions

class ServiceConnectionException private constructor(
    errorMessage: String
) : BaseException(errorMessage, 500) {
    companion object {
        val CANNOT_CONNECT_EXCEPTION = ServiceConnectionException("Cannot Connect to service")
    }
}
