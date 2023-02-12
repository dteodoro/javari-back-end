package com.dteodoro.javari.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dteodoro.javari.domain.bettor.BettorService;
import com.dteodoro.javari.dto.BettorDTO;
import com.dteodoro.javari.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "${javari.client.front}")
@RestController
@RequestMapping("/bettor")
@RequiredArgsConstructor
public class BettorController {

	private final BettorService bettorService;
	private final UserRepository repo;

	@GetMapping("/{id}")
	public ResponseEntity<BettorDTO> getBettorDetail(@PathVariable("id") UUID bettorId){
		//TODO check if bettor id is the same bettor current login
		if(bettorId != null) {
			return ResponseEntity.ok(bettorService.findBettorDetails(bettorId));
		}
		return ResponseEntity.notFound().build();
	}
	
}

