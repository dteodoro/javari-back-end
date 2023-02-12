package com.dteodoro.javari.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dteodoro.javari.dto.LoginDTO;
import com.dteodoro.javari.dto.UserDTO;
@CrossOrigin(origins = "${javari.client.front}")
@RestController
public class SessionController {

	@PostMapping("/sessions")
	@CrossOrigin(origins = "${javari.client.front}")
	public UserDTO login(@RequestBody LoginDTO loging) {
		System.out.println("Logando o usuário: "+loging.getUsername()+", com a senha: "+loging.getPassword());
		return new UserDTO("12346589",true);
	}
	
}
