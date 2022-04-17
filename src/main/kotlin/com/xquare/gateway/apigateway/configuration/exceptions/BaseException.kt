package com.xquare.gateway.apigateway.configuration.exceptions

abstract class BaseException(
    val errorMessage: String,
    val statusCode: Int
) : RuntimeException(errorMessage)
