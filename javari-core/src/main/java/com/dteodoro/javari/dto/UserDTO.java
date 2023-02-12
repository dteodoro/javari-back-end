package com.dteodoro.javari.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
	private String token;
	private boolean userLogged;
}
