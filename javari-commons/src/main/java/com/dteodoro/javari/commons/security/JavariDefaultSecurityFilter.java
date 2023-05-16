package com.dteodoro.javari.commons.security;

import com.dteodoro.javari.commons.dto.UserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JavariDefaultSecurityFilter extends OncePerRequestFilter implements JavariSecurityFilter {

	private final JavariAuthClient authClient;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		var tokenJWT = request.getHeader("Authorization");

		if (tokenJWT != null) {
			try {
				UserDTO user = getAuthClient().validateToken(tokenJWT);
				if (user != null) {
					var authentication = new UsernamePasswordAuthenticationToken(user, tokenJWT, user.getAuthorities().stream().map(SimpleGrantedAuthority::new).toList());
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}catch (Exception e){
				log.error("erro ao buscar o usuário ",e);
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public JavariAuthClient getAuthClient() {
		return authClient;
	}
}
