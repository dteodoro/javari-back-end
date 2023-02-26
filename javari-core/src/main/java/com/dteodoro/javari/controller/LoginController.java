package com.dteodoro.javari.controller;

import com.dteodoro.javari.domain.bettor.Bettor;
import com.dteodoro.javari.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import java.net.URI;
import java.util.Collections;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(originPatterns = {"http://localhost:3000/"},allowedHeaders = {"*"})
public class LoginController {
	
	private final AuthenticationManager authManager;
	private final TokenService tokenService;
	private final AppUserService userService;
	private final PasswordEncoder passwordEncoder;

	@PostMapping("/login")
	public ResponseEntity<TokenJWTDTO> login(@RequestBody @Valid UserData userData) {
		var authenticationToken = new UsernamePasswordAuthenticationToken(userData.username(),userData.password());
		var authentication = authManager.authenticate(authenticationToken);
		var tokenJWT = tokenService.generateToken((AppUser) authentication.getPrincipal());
		return ResponseEntity.ok(new TokenJWTDTO(tokenJWT,true));
	}

	@PostMapping("/signIn")
	public ResponseEntity<?> registerUser(@RequestBody @Valid UserData userData){
		if(userService.existsByUsername(userData.username())){
			return ResponseEntity.badRequest().build();
		}
		Bettor bettor = new Bettor();
		bettor.setUsername(userData.username());
		bettor.setPassword(passwordEncoder.encode(userData.password()));
		bettor.setCurrentPosition(0);
		bettor.setPreviousPosition(0);
		bettor.setAuthorities(userService.getAuthority("ROLE_USER"));
		userService.save(bettor);
		return ResponseEntity.created(URI.create("/bettor/"+bettor.getId())).build();
	}
	
}
