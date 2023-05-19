package com.dteodoro.javari.game.controller;

import com.dteodoro.javari.commons.dto.SeasonDTO;
import com.dteodoro.javari.game.service.SeasonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/game/seasons")
public class SeasonController {

	private final SeasonService seasonService;

	@PostMapping
	public ResponseEntity saveSeason(@RequestBody @Valid SeasonDTO seasonDTO){
		seasonService.save(seasonDTO);
		return ResponseEntity.ok().build();
	}

}
