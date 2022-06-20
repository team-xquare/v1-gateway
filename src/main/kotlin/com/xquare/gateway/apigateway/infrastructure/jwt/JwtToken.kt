package com.xquare.gateway.apigateway.infrastructure.jwt

import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jwt.SignedJWT
import com.xquare.gateway.apigateway.configuration.filter.authentication.exception.InvalidJwtException
import com.xquare.gateway.apigateway.configuration.filter.authentication.jwt.properties.JwtProperties
import java.util.Date
import org.springframework.stereotype.Component

@Component
class JwtToken(
    private val jwtProperties: JwtProperties
) : JwtTokenParser {
    override fun parseToken(token: String): Map<String, Any> {
        val signedJWT = SignedJWT.parse(token)
        signedJWT.checkExpired()
        signedJWT.checkValidSignature()
        return signedJWT.jwtClaimsSet.toJSONObject()
    }

    private fun SignedJWT.checkExpired() {
        // 현재 시간과 비교해서 만료일이 현재보다 과거면
        if (this.jwtClaimsSet.expirationTime.before(Date())) {
            throw InvalidJwtException.JWT_EXPIRED_EXCEPTION
        }
    }

    private fun SignedJWT.checkValidSignature() {
        val verifier = MACVerifier(jwtProperties.secretKey)
        val isValidSignature = this.verify(verifier)

        if (!isValidSignature) {
            throw InvalidJwtException.INVALID_JWT_SIGNATURE_EXCEPTION
        }
    }
}
