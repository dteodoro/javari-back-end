package com.dteodoro.javari.connector.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

	private final SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.cors().and().csrf().disable() //disable CORS and CSRF 'cause it will be validated by Token
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //disable Session 'cause it will be an STATELESS API
				.and().authorizeHttpRequests() //Configure HttpRequest
				.anyRequest().authenticated() //any other request must be authenticated
				.and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // add SecurityFilter to verify token before spring default filter
				.build(); // build the SercurityFilterChain
	}

}
