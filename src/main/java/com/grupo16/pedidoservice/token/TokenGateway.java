package com.grupo16.pedidoservice.token;

public interface TokenGateway {

	Object getInfoFromToken(String key, String token);

}
