package com.dteodoro.javari.commons.security;

import com.dteodoro.javari.commons.dto.AuthenticationRequest;
import com.dteodoro.javari.commons.dto.UserDTO;

public interface JavariAuthClient {
	UserDTO validateToken(String token);
}
