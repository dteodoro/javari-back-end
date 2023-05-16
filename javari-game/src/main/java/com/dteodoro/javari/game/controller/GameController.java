package com.dteodoro.javari.game.controller;


import com.dteodoro.javari.commons.dto.RankDTO;
import com.dteodoro.javari.commons.dto.StandingDTO;
import com.dteodoro.javari.commons.enumeration.NFLConference;
import com.dteodoro.javari.commons.enumeration.SeasonType;
import com.dteodoro.javari.game.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/v2/game")
@RequiredArgsConstructor
public class GameController {

	private final TeamService teamService;

	@GetMapping("/rank")
	public ResponseEntity<RankDTO> getRank(int year, SeasonType sessionType){
//		return ResponseEntity.ok(rankService.getRank(year));
		return null;
	}
	
	@GetMapping("/rank/update")
	public void updateRank() {
//		rankService.updateRank();
	}
	
	@GetMapping("/standing/{conference}")
	public ResponseEntity<List<StandingDTO>> getStanding(@PathVariable("conference") NFLConference conference){
		return ResponseEntity.ok(teamService.findStandingByConference(conference));
	}
	
}

