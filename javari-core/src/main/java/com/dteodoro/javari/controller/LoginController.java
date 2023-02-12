package com.dteodoro.javari.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dteodoro.javari.domain.bettor.AppUser;
import com.dteodoro.javari.domain.bettor.UserData;
import com.dteodoro.javari.security.TokenJWTDTO;
import com.dteodoro.javari.security.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
@CrossOrigin(originPatterns = {"http://localhost:3000/"},allowedHeaders = {"*"})
public class LoginController {
	
	private final AuthenticationManager authManager;
	private final TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenJWTDTO> login(@RequestBody @Valid UserData userData) {
		var authenticationToken = new UsernamePasswordAuthenticationToken(userData.username(),userData.password());
		var authentication = authManager.authenticate(authenticationToken);
		var tokenJWT = tokenService.generateToken((AppUser) authentication.getPrincipal());
		return ResponseEntity.ok(new TokenJWTDTO(tokenJWT,true));
	}
	
}
