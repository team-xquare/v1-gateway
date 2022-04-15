package com.xquare.gateway.apigateway.user

import com.xquare.gateway.apigateway.user.destinations.UserProperty
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod

@Configuration
class UserRouteLocator(
    private val routeLocatorBuilder: RouteLocatorBuilder
) {

    @Bean
    fun userProxyRouting(userProperty: UserProperty): RouteLocator =
        routeLocatorBuilder.routes()
            .route {
                it.path("/users").and()
                    .method(HttpMethod.GET)
                    .uri(userProperty.destination.userServiceUrl)
            }
            .build()
}
