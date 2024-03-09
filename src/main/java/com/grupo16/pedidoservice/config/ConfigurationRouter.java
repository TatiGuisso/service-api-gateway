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
	    		
	    		.route("produto-service-route", r -> r.path("/produtos/**").uri("lb://PRODUTO-SERVICE"))
	    		.route("carrinho-service-route", r -> r.path("/carrinhos/**").uri("lb://CARRINHO	-SERVICE"))
	    		.route("pedido-service-route", r -> r.path("/pedidos/**").uri("lb://PEDIDO-SERVICE"))
//	            .route(p -> p
//	                    .path("/get")
//	                    //.filters(f -> f.addRequestHeader("Hello", "World"))
//	                    .uri("http://httpbin.org:80"))
	    		.build();
	}
	
}
