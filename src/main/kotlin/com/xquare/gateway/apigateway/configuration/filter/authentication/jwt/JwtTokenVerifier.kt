package com.xquare.gateway.apigateway.configuration.filter.authentication.jwt

import com.nimbusds.jwt.JWT
import com.nimbusds.jwt.JWTParser
import com.xquare.gateway.apigateway.configuration.filter.authentication.exception.InvalidJwtException
import com.xquare.gateway.apigateway.configuration.filter.authentication.jwt.JwtTokenParsingHelper.isJwtTokenExpired
import java.text.ParseException

object JwtTokenVerifier {
    fun parseJwtToken(pureToken: String): JWT {
        val parsedJwt = try {
            JWTParser.parse(pureToken)
        } catch (e: ParseException) {
            throw InvalidJwtException(e.message!!)
        }

        if (parsedJwt.jwtClaimsSet.isJwtTokenExpired()) {
            throw InvalidJwtException(InvalidJwtException.JWT_EXPIRED_MESSAGE)
        }

        return parsedJwt
    }
}
