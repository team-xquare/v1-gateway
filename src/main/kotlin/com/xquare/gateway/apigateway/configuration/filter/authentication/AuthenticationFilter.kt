package com.xquare.gateway.apigateway.configuration.filter.authentication

import com.xquare.gateway.apigateway.configuration.filter.authentication.jwt.JwtTokenParsingHelper.getAuthorizationFromHeader
import com.xquare.gateway.apigateway.configuration.filter.authentication.jwt.JwtTokenParsingHelper.removeJwtTokenPrefix
import com.xquare.gateway.apigateway.infrastructure.jwt.JwtTokenParser
import java.util.UUID
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthenticationFilter(
    private val jwtTokenParser: JwtTokenParser,
) : GlobalFilter {

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        val bearerToken = request.headers.getAuthorizationFromHeader()

        val requestBuilder = request.mutate()

        requestBuilder.header("Request-Id", UUID.randomUUID().toString())

        bearerToken?.let {
            val pureToken = it.removeJwtTokenPrefix()
            val jwtTokenClaims = jwtTokenParser.parseToken(pureToken)

            val subject = jwtTokenClaims["sub"]!!.toString()
            val authorities = jwtTokenClaims["authorities"]!!.toString()
            val role = jwtTokenClaims["role"]!!.toString()

            requestBuilder.header("Request-User-Authority", authorities)
            requestBuilder.header("Request-User-Id", subject)
            requestBuilder.header("Request-User-Role", role)
        }

        val modifiedRequest = requestBuilder.build()

        val modifiedExchange = exchange.mutate()
            .request(modifiedRequest)
            .build()

        return chain.filter(modifiedExchange)
    }
}
