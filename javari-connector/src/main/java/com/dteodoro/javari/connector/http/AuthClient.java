package com.dteodoro.javari.connector.http;

import com.dteodoro.javari.commons.dto.AuthenticationRequest;
import com.dteodoro.javari.commons.dto.AuthenticationResponse;
import com.dteodoro.javari.commons.dto.UserDTO;
import com.dteodoro.javari.commons.security.JavariAuthClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@FeignClient("javari-auth-service")
public interface AuthClient extends JavariAuthClient {
	@PostMapping(value = "/api/v1/auth/validate-token", consumes = MediaType.APPLICATION_JSON_VALUE)
	UserDTO validateToken(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

	@PostMapping(value = "/api/v1/auth/authenticate")
	ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);

}
