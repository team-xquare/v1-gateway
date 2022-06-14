package com.xquare.gateway.apigateway.configuration.filter.authentication

import com.xquare.gateway.apigateway.configuration.filter.authentication.jwt.JwtTokenParsingHelper.getAuthorizationFromHeader
import com.xquare.gateway.apigateway.configuration.filter.authentication.jwt.JwtTokenParsingHelper.removeJwtTokenPrefix
import com.xquare.gateway.apigateway.infrastructure.jwt.JwtTokenParser
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.cloud.sleuth.Tracer
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthenticationFilter(
    private val jwtTokenParser: JwtTokenParser,
    private val tracer: Tracer
) : GlobalFilter {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        val bearerToken = request.headers.getAuthorizationFromHeader()

        bearerToken?.let {

            val span = tracer.currentSpan()!!

            val pureToken = it.removeJwtTokenPrefix()
            val jwtTokenClaims = jwtTokenParser.parseToken(pureToken)
            val subject = jwtTokenClaims["sub"]!!.toString()
            val authorities = jwtTokenClaims["authorities"]!!.toString()
            val role = jwtTokenClaims["role"]!!.toString()

            exchange.request.headers["Request-User-Authority"] = authorities
            exchange.request.headers["Request-User-Id"] = subject
            exchange.request.headers["Request-User-Role"] = role
            exchange.request.headers["Request-Id"] = span.context().spanId()
        }

        return chain.filter(exchange)
    }
}
