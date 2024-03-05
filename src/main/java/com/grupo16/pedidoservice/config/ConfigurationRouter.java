package com.grupo16.pedidoservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationRouter {

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
	    return builder.routes()
	    		
	    		.route("pedido-route", r -> r.path("/pedidos/**").uri("lb://PEDIDO-SERVICE"))
//	            .route(p -> p
//	                    .path("/get")
//	                    //.filters(f -> f.addRequestHeader("Hello", "World"))
//	                    .uri("http://httpbin.org:80"))
	    		.build();
	}
	
}
