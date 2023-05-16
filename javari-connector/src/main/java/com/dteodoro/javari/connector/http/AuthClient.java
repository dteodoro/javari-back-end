package com.dteodoro.javari.connector.http;

import com.dteodoro.javari.commons.dto.UserDTO;
import com.dteodoro.javari.commons.security.JavariAuthClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
@FeignClient("javari-auth-service")
public interface AuthClient extends JavariAuthClient {
	@PostMapping(value = "/api/v1/auth/validate-token", consumes = MediaType.APPLICATION_JSON_VALUE)
	UserDTO validateToken(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);
}
