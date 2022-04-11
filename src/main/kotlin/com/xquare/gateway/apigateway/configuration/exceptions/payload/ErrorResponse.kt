package com.xquare.gateway.apigateway.configuration.exceptions.payload

data class ErrorResponse(
    private val errorMessage: String,
    private val statusCode: Int
)