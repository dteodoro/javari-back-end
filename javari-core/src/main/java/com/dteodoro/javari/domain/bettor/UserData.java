package com.dteodoro.javari.domain.bettor;

import jakarta.validation.constraints.NotBlank;

public record UserData(
		@NotBlank(message = "username cannot be null") String username,
		@NotBlank(message = "password cannot be null") String password) {
}
