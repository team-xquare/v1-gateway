package com.xquare.gateway.apigateway.configuration.exceptions.payload

data class ErrorResponse(
    val errorMessage: String,
    val statusCode: Int
)