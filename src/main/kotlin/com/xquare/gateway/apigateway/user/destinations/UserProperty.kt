package com.xquare.gateway.apigateway.user.destinations

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
    val userServiceUrl: String
)
