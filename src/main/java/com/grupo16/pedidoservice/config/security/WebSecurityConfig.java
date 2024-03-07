package com.grupo16.pedidoservice.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.WebFilter;

@Configuration
//@EnableWebSecurity
@EnableWebFluxSecurity
public class WebSecurityConfig {

	//@Bean
	//@Order(Ordered.HIGHEST_PRECEDENCE)
	public WebFilter tokenInterceptorFilter(/*TokenGateway tokenGateway*/) {
		return new TokenInterceptorFilter(/*tokenGateway*/);
	}

	@Bean
	//@DependsOn({"tokenInterceptorFilter"})
	public SecurityWebFilterChain filterChain(ServerHttpSecurity serverHttpSecurity/*, TokenInterceptorFilter tokenInterceptorFilter*/) throws Exception {
		serverHttpSecurity.csrf(csrf -> csrf.disable());

		//https://stackoverflow.com/questions/50015711/spring-security-webflux-body-with-authentication
		
		//serverHttpSecurity.securityContextRepository(NoOpServerSecurityContextRepository.getInstance());
		
		return serverHttpSecurity.authorizeExchange(
				exchanges -> exchanges
				.anyExchange().permitAll()
//				.pathMatchers("/ale/pedidos").hasRole("ADMIN")///Concatena "ROLE_"
//				.pathMatchers("/ale/pedidos").hasAuthority("ADMIN")
//				.anyExchange().authenticated()
				)
				//.addFilterAfter(tokenInterceptorFilter, SecurityWebFiltersOrder.FIRST)
				//.addFilterAt(tokenInterceptorFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				//.addFilterAt(tokenInterceptorFilter, SecurityWebFiltersOrder.SECURITY_CONTEXT_SERVER_WEB_EXCHANGE)
				//.addFilterAt(tokenInterceptorFilter, SecurityWebFiltersOrder.AUTHORIZATION)
				.build();
	}
}
