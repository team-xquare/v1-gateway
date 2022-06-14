package com.xquare.gateway.apigateway.infrastructure.user.destinations

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.NestedConfigurationProperty

@ConstructorBinding
@ConfigurationProperties(prefix = "services.user")
class UserProperty(
    @NestedConfigurationProperty
    val destination: UserDestinationProperty
)

@ConstructorBinding
class UserDestinationProperty(
    val userServiceHost: String,
    val userEndpointProperty: UserEndpointProperty
)

@ConstructorBinding
class UserEndpointProperty(
    val checkLoginAvailableEndpoint: String
)
