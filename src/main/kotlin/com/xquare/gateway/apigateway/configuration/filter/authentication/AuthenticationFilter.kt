package com.xquare.gateway.apigateway.configuration.filter.authentication

import com.xquare.gateway.apigateway.configuration.filter.authentication.jwt.JwtTokenParsingHelper.getAuthorizationFromHeader
import com.xquare.gateway.apigateway.configuration.filter.authentication.jwt.JwtTokenParsingHelper.removeJwtTokenPrefix
import com.xquare.gateway.apigateway.configuration.filter.authentication.jwt.JwtTokenVerifier
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * This filter authenticate user's token.
 * Also add user_id, user_role to request header.
 */
@Component
class AuthenticationFilter : GlobalFilter {

    companion object {
        private const val USER_ID_HEADER = "User-Id"
        private const val USER_ROLE_HEADER = "User-Role"
    }

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        val bearerToken = request.headers.getAuthorizationFromHeader()

        bearerToken?.let {
            val pureToken = it.removeJwtTokenPrefix()
            val parsedJwt = JwtTokenVerifier.parseJwtToken(pureToken)

            val userId = parsedJwt.jwtClaimsSet.subject
            val userRole = parsedJwt.jwtClaimsSet.getClaim("role")

            request.headers.add(USER_ID_HEADER, userId)
            request.headers.add(USER_ROLE_HEADER, userRole as String)
        }

        return chain.filter(exchange)
    }
}