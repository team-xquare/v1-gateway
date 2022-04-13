package com.xquare.gateway.apigateway.configuration.filter.authentication

import com.nimbusds.jwt.JWTClaimsSet
import com.xquare.gateway.apigateway.configuration.filter.authentication.JWTClaimsSetExtensions.getUserId
import com.xquare.gateway.apigateway.configuration.filter.authentication.JWTClaimsSetExtensions.getUserRole

data class UserInfoHeader(
    val userId: String,
    val userRole: String
) {
    constructor(jwtClaimsSet: JWTClaimsSet) : this(
        userId = jwtClaimsSet.getUserId(),
        userRole = jwtClaimsSet.getUserRole()
    )
}