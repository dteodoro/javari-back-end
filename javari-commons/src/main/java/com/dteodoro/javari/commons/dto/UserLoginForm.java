package com.dteodoro.javari.commons.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginForm(
		@NotBlank(message = "username cannot be null") String username,
		@NotBlank(message = "password cannot be null") String password) {
}
