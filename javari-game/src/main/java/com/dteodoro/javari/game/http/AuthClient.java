package com.dteodoro.javari.game.http;

import com.dteodoro.javari.commons.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient("javari-auth-service")
public interface AuthClient {
	@PostMapping(value = "/api/v1/auth/validate-token", consumes = MediaType.APPLICATION_JSON_VALUE)
	UserDTO validateToken(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);
}
