package com.xquare.gateway.apigateway.configuration.exceptions.handler

import com.xquare.gateway.apigateway.configuration.exceptions.BaseException
import com.xquare.gateway.apigateway.configuration.exceptions.InternalServerError
import com.xquare.gateway.apigateway.configuration.exceptions.ServiceConnectionException
import com.xquare.gateway.apigateway.configuration.exceptions.payload.ErrorResponse
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.ConnectException

@Component
class ErrorWebExchangeHandler(
    errorAttributes: ErrorAttributes,
    webProperties: WebProperties,
    applicationContext: ApplicationContext,
    serverCodecConfigurer: ServerCodecConfigurer
) : AbstractErrorWebExceptionHandler(
    errorAttributes,
    webProperties.resources,
    applicationContext
) {

    init {
        super.setMessageReaders(serverCodecConfigurer.readers)
        super.setMessageWriters(serverCodecConfigurer.writers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> =
        RouterFunctions.route(RequestPredicates.all(), this::handleError)

    private fun handleError(request: ServerRequest): Mono<ServerResponse> =
        when (val throwable = super.getError(request)) {
            is BaseException -> buildErrorResponse(throwable)
            is ConnectException -> buildErrorResponse(ServiceConnectionException.CANNOT_CONNECT_EXCEPTION)
            else -> buildErrorResponse(InternalServerError.UNCAUGHT_EXCEPTION_OCCURRED)
        }

    private fun buildErrorResponse(baseException: BaseException) =
        ServerResponse.status(baseException.statusCode)
            .bodyValue(
                ErrorResponse(
                    errorMessage = baseException.errorMessage,
                    statusCode = baseException.statusCode
                )
            )

}