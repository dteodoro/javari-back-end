package com.dteodoro.javari.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.dteodoro.javari.domain.user.BaseUser;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	public String generateToken(BaseUser user) {
		try {
		    var algorithm = Algorithm.HMAC256(secret);
		   return JWT.create()
		        .withIssuer("API javari")
		        .withSubject(user.getUsername())
		        .withClaim("id",user.getId().toString())
		        .withExpiresAt(expiresDate())
		        .sign(algorithm);
		} catch (JWTCreationException exception){
		    throw new RuntimeException("Error cannot generate token jwt",exception);
		}
	}

	private Instant expiresDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}

	public String getUserName(String tokenJWT) {
		try {
		    var algorithm = Algorithm.HMAC256(secret);
		   return JWT.require(algorithm)
		        .withIssuer("API javari")
		        .build()
		        .verify(tokenJWT)
		        .getSubject();
		} catch (JWTCreationException exception){
		    throw new RuntimeException("invalid or expired token",exception);
		}
	}

}
