package com.dteodoro.javari.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MockCryptPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return true;
	}

}
