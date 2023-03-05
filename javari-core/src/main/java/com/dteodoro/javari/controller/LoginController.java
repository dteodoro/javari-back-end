package com.dteodoro.javari.controller;

import com.dteodoro.javari.domain.user.AppUser;
import com.dteodoro.javari.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dteodoro.javari.domain.user.BaseUser;
import com.dteodoro.javari.domain.user.UserLoginForm;
import com.dteodoro.javari.security.TokenJWTDTO;
import com.dteodoro.javari.security.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(originPatterns = {"http://localhost:3000/"},allowedHeaders = {"*"})
public class LoginController {
	
	private final AuthenticationManager authManager;
	private final TokenService tokenService;
	private final AppUserService userService;

	@PostMapping("/login")
	public ResponseEntity<TokenJWTDTO> login(@RequestBody @Valid UserLoginForm userData) {
		var authenticationToken = new UsernamePasswordAuthenticationToken(userData.username().toLowerCase(),userData.password());
		var authentication = authManager.authenticate(authenticationToken);
		var user = (BaseUser) authentication.getPrincipal();
		var tokenJWT = tokenService.generateToken(user);
		return ResponseEntity.ok(new TokenJWTDTO(tokenJWT,true,user.getUsername(),user.getId()));
	}

	@PostMapping("/signIn")
	public ResponseEntity<?> registerUser(@RequestBody @Valid UserLoginForm userData){
		if(userService.existsByUsername(userData.username().toLowerCase())){
			return ResponseEntity.badRequest().build();
		}
		AppUser user = userService.createUser(userData.username().toLowerCase(), userData.password());
		return ResponseEntity.created(URI.create("/bettor/"+user.getId())).build();
	}
	
}
