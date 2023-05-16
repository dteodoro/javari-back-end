package com.dteodoro.javari.auth.controller;

import com.dteodoro.javari.auth.dto.AuthenticationRequest;
import com.dteodoro.javari.auth.dto.AuthenticationResponse;
import com.dteodoro.javari.auth.dto.RegisterRequest;
import com.dteodoro.javari.commons.dto.UserDTO;
import com.dteodoro.javari.auth.service.AuthenticationService;
import com.dteodoro.javari.core.domain.BaseUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthenticationService service;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(
			@RequestBody RegisterRequest request
	) {
		return ResponseEntity.ok(service.register(request));
	}
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest request
	) {
		return ResponseEntity.ok(service.authenticate(request));
	}
	@PostMapping("/refresh-token")
	public void refreshToken(
			HttpServletRequest request,
			HttpServletResponse response
	) throws IOException {
		service.refreshToken(request, response);
	}

	@PostMapping("/validate-token")
	public ResponseEntity<UserDTO> validateToken(
			HttpServletRequest request,
			HttpServletResponse response
	){

		try{
			BaseUser user = service.validateToken(request.getHeader(HttpHeaders.AUTHORIZATION));
			if(user != null){
				var userDto = UserDTO.builder()
						.username(user.getUsername())
						.id(user.getId())
						.authorities(user.getAuthorities().stream().map(Object::toString).toList())
						.build();
				return ResponseEntity.ok(userDto);
			}
			return ResponseEntity.notFound().build();
		}catch (IOException e) {
			log.error("cannot load user by token, error message: {}",e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}
}
