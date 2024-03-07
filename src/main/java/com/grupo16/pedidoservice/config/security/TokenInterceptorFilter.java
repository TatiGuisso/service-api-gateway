package com.grupo16.pedidoservice.config.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebSession;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
public class TokenInterceptorFilter implements WebFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String USUARIO_ID = "usuarioId";
    private static final String USUARIO_PERFIL = "usuarioPerfil";
    
    //private TokenGateway tokenGateway;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain filterChain) {
		
		Mono<WebSession> session = exchange.getSession();
		
		
		
		ServerHttpRequest request = exchange.getRequest();
		
		if( HttpMethod.OPTIONS.equals(request.getMethod()) || request.getURI().getPath().contains("actuator")) {
			return filterChain.filter(exchange);
		}
		
		HttpHeaders headers = request.getHeaders();
		HttpServletRequest requestWrapper = new HeaderMapRequestWrapper(headers);
		
		try {
			

			List<String> authorizations = headers.get(HttpHeaders.AUTHORIZATION);
			if(authorizations == null || authorizations.isEmpty()) {
				return filterChain.filter(exchange);
			}
			final String baererToken = authorizations.get(0);
			if(isValidToken(baererToken)) {
				
				final String token = baererToken.substring(7, baererToken.length());
				
				final Map<String, String> headersInfos = getInfos(token);

				addTokenInfosIntoHeader((HeaderMapRequestWrapper) requestWrapper, headersInfos);
				
				createAutentication(headersInfos);
			}
			return filterChain.filter(exchange);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		} 
	} 

	private void createAutentication(final Map<String, String> headersInfos) {
		String role = headersInfos.get(USUARIO_PERFIL);
		Collection<? extends GrantedAuthority> authorities = role == null ? null : Arrays.asList(() -> role);
		Authentication auth = new UsernamePasswordAuthenticationToken("admin", "", authorities);
		//SecurityContextHolder.getContext().setAuthentication(auth);
		//SecurityContextHolder.getContextHolderStrategy().getContext().setAuthentication(auth);
		
		ReactiveSecurityContextHolder.withAuthentication(auth);
//		ReactiveSecurityContextHolder.getContext().doOnSuccess(sc -> {
//			System.out.println(sc);
//			sc.setAuthentication(auth);	
//		});
	}

	
	private Boolean isValidToken(String token) {
		//FIXME
//		if (token == null || token.isEmpty() || !token.startsWith(TOKEN_PREFIX)) {
//			return false;
//		}
		return true;
	}

	
	private void addTokenInfosIntoHeader(final HeaderMapRequestWrapper requestWrapper, final Map<String, String> headersInfos) {	
		requestWrapper.addHeader(USUARIO_ID, headersInfos.get(USUARIO_ID));
		requestWrapper.addHeader(USUARIO_PERFIL, headersInfos.get(USUARIO_PERFIL));
	}
	
	private Map<String, String> getInfos(final String token){
		final Map<String, String> loggedInfos = new HashMap<>();
		//FIXME
//		loggedInfos.put(USUARIO_ID, tokenGateway.getInfoFromToken(USUARIO_ID, token).toString());
//		loggedInfos.put(USUARIO_PERFIL, tokenGateway.getInfoFromToken(USUARIO_PERFIL, token).toString());
		loggedInfos.put(USUARIO_ID, 1+"");
		loggedInfos.put(USUARIO_PERFIL, "ADMIN");

		return loggedInfos;
	}
}
