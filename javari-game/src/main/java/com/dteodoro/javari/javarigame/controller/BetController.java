package com.dteodoro.javari.javarigame.controller;

import com.dteodoro.javari.dto.BetDTO;
import com.dteodoro.javari.javarigame.service.BetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game/bets")
public class BetController {

	private final BetService betService;

	@PostMapping
	public ResponseEntity<BetDTO> makeBet(@RequestBody BetDTO betDto) {
		return betService.makeBet(betDto);
	}

	@GetMapping("/{bettorId}")
	public ResponseEntity<List<BetDTO>> findLastBets(@PathVariable("bettorId") UUID bettorId) {
		return ResponseEntity.ok(betService.getLastBets(bettorId));
	}
}
