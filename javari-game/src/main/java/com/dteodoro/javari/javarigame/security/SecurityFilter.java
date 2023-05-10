package com.dteodoro.javari.javarigame.security;

import com.dteodoro.javari.dto.UserDTO;
import com.dteodoro.javari.javaricore.domain.BaseUser;
import com.dteodoro.javari.javaricore.repository.UserRepository;
import com.dteodoro.javari.javarigame.http.AuthClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {

	private final AuthClient authClient;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		var tokenJWT = request.getHeader("Authorization");

		if (tokenJWT != null) {
			try {
				UserDTO user = authClient.validateToken(tokenJWT);
				if (user != null) {
					var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities().stream().map(SimpleGrantedAuthority::new).toList());
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}catch (Exception e){
				log.error("erro ao buscar o usuário ",e);
			}
		}
		filterChain.doFilter(request, response);
	}

	private String getTokenByHeader(HttpServletRequest request) {
		var authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null) {
			return authorizationHeader.replace("Bearer ", "");
		}
		return null;
	}


}
