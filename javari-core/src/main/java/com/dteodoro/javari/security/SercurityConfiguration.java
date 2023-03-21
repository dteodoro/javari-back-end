package com.dteodoro.javari.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SercurityConfiguration {
	
	private final SecurityFilter securityFilter;
	@Value("${javari.client.front}")
	private String frontEndURI;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.cors().and().csrf().disable() //disable CORS and CSRF 'cause it will be validated by Token
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //disable Session 'cause it will be an STATELESS API
			.and().authorizeHttpRequests() //Configure HttpRequest
			.requestMatchers(HttpMethod.GET,"/actuator/**").permitAll() // permit actuator endpoints
			.requestMatchers(HttpMethod.POST, "/auth/login","/auth/signIn").permitAll() // permit access to login URI through POST method
			.anyRequest().authenticated() //any other request must be authenticated
			.and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // add SecurityFilter to verify token before spring default filter
			.build(); // build the SercurityFilterChain
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}	

}
