package com.dteodoro.javari.game.controller;

import com.dteodoro.javari.commons.dto.BetDTO;
import com.dteodoro.javari.game.service.BetService;
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
