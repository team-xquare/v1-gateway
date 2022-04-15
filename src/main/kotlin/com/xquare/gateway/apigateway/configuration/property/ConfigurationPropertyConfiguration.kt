package com.xquare.gateway.apigateway.configuration.property

import com.xquare.gateway.apigateway.BASE_PACKAGE
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackages = [BASE_PACKAGE])
class ConfigurationPropertyConfiguration
