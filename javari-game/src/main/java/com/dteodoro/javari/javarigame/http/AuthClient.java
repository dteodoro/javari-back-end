package com.dteodoro.javari.javarigame.http;

import com.dteodoro.javari.dto.UserDTO;
import com.dteodoro.javari.javaricore.domain.BaseUser;
import feign.HeaderMap;
import org.apache.catalina.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient("javari-auth-service")
public interface AuthClient {
	@PostMapping(value = "/api/v1/auth/validate-token", consumes = MediaType.APPLICATION_JSON_VALUE)
	UserDTO validateToken(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);
}
