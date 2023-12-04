package com.dteodoro.javari.game.security;

import com.dteodoro.javari.commons.dto.UserDTO;
import com.dteodoro.javari.game.http.AuthClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {

	private final AuthClient authClient;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		var tokenJWT = request.getHeader("Authorization");
		log.info("[GAME] - SecurityFilter - token: " + tokenJWT);
		if (tokenJWT != null) {
			try {
				log.info("[GAME] - SecurityFilter - validate token...");
				UserDTO user = authClient.validateToken(tokenJWT);
				log.info("[GAME] - SecurityFilter - user: " + user);

				if (user != null) {
					var authentication = new UsernamePasswordAuthenticationToken(user, null,
							user.getAuthorities().stream().map(SimpleGrantedAuthority::new).toList());
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			} catch (Exception e) {
				log.error("erro ao buscar o usuário ", e);
			}
		}
		filterChain.doFilter(request, response);
	}

}
