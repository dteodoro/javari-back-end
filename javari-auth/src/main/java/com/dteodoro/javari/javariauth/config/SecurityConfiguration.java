package com.dteodoro.javari.javariauth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;
	private final LogoutHandler logoutHandler;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.cors().and().csrf().disable() //disable CORS and CSRF 'cause it will be validated by Token
				.authorizeHttpRequests() //Configure HttpRequest
					.requestMatchers("/api/v1/auth/**").permitAll()
					.anyRequest().authenticated() //any other request must be authenticated
				.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //disable Session 'cause it will be an STATELESS API
				.and()
				.authenticationProvider(authenticationProvider)
					.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // add jwtAuthFilter to verify token before spring default filter
					.logout()
					.logoutUrl("/api/v1/auth/logout")
						.addLogoutHandler(logoutHandler)
						.logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
				.and()
				.build(); // build the SercurityFilterChain
	}

}
