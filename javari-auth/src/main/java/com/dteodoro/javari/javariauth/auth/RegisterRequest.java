package com.dteodoro.javari.javariauth.auth;

import com.dteodoro.javari.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	private String firstname;
	private String lastname;
	private String email;
	private String password;

}
