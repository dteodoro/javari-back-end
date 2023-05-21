package com.dteodoro.javari.game.controller;

import com.dteodoro.javari.commons.dto.BettorDTO;
import com.dteodoro.javari.commons.dto.TeamDTO;
import com.dteodoro.javari.game.service.BettorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/game/bettor")
@RequiredArgsConstructor
public class BettorController {

	private final BettorService bettorService;

	@GetMapping("/{id}")
	public ResponseEntity<BettorDTO> getBettorDetail(@PathVariable("id") UUID bettorId){
		//TODO check if bettor id is the same bettor current login
		if(bettorId != null) {
			return ResponseEntity.ok(bettorService.findBettorDetails(bettorId));
		}
		return ResponseEntity.notFound().build();
	}
	@GetMapping("/{id}/rivals")
	public ResponseEntity<List<BettorDTO>> getRivals(@PathVariable("id") UUID bettorId){
		if(bettorId != null) {
			List<BettorDTO> rivals = bettorService.findRivals(bettorId);
			return ResponseEntity.ok(rivals);
		}
		return ResponseEntity.notFound().build();
	}



	@PostMapping("/{id}/favoriteTeam/{teamId}")
	public TeamDTO setFavoriteTeam(@PathVariable("id") UUID bettorId, @PathVariable("teamId") UUID teamId){
		return bettorService.setFavoriteTeam(bettorId,teamId);
	}
	@GetMapping("/{id}/favoriteTeam")
	public TeamDTO getFavoriteTeam(@PathVariable("id")UUID bettorId){
		return bettorService.findFavoriteTeam(bettorId);
	}
	@DeleteMapping("/{id}/favoriteTeam/{teamId}")
	public void removeFavoriteTeam(@PathVariable("id") UUID bettorId,@PathVariable("teamId") UUID teamId){
		bettorService.setFavoriteTeam(bettorId,null);
	}
	@GetMapping("/{id}/{roleName}")
	public boolean hasPermission(@PathVariable(name="id",required = true)UUID bettorId,@PathVariable(name="roleName",required = true) String roleName){
		return bettorService.hasPermission(bettorId,roleName);
	}

}
