package com.dteodoro.javari.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dteodoro.javari.domain.bet.BetService;
import com.dteodoro.javari.dto.BetDTO;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "${javari.client.front}")
@RestController
@RequiredArgsConstructor
@RequestMapping("/bets")
public class BetController {
	
	private final BetService betService;
	
	@PostMapping
	public ResponseEntity<BetDTO> makeBet(@RequestBody BetDTO betDto){
		return betService.makeBet(betDto);
	}
	
	@GetMapping("/{bettorId}")
	public ResponseEntity<List<BetDTO>> findLastBets(@PathVariable("bettorId") UUID bettorId){
		return ResponseEntity.ok(betService.getLastBets(bettorId));
	}
}
