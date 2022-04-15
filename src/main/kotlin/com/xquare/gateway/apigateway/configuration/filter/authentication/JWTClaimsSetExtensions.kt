package com.xquare.gateway.apigateway.configuration.filter.authentication

import com.nimbusds.jwt.JWTClaimsSet

object JWTClaimsSetExtensions {
    fun JWTClaimsSet.getUserId() = this.getClaim("user_id").toString()
    fun JWTClaimsSet.getUserRole() = this.getClaim("user_id").toString()
}
