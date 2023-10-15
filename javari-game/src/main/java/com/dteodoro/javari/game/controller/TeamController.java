package com.dteodoro.javari.game.controller;

import com.dteodoro.javari.commons.dto.TeamScoreDTO;
import com.dteodoro.javari.commons.dto.ConferenceTeamsDTO;
import com.dteodoro.javari.commons.dto.TeamDTO;
import com.dteodoro.javari.commons.enumeration.NFLConference;
import com.dteodoro.javari.commons.enumeration.NFLDivision;
import com.dteodoro.javari.game.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/game/teams")
@RequiredArgsConstructor
public class TeamController {

	private final TeamService teamService;

	@GetMapping
	public List<ConferenceTeamsDTO> findAll(@RequestParam(name = "conference") String conference,
			@RequestParam(name = "division") String division) {

		return teamService.findByTypes(StringUtils.hasText(conference) ? NFLConference.valueOf(conference) : null,
				StringUtils.hasText(division) ? NFLDivision.valueOf(division) : null);
	}

	@GetMapping("/{teamId}")
	public TeamDTO findById(@PathVariable(name = "teamId", required = true) UUID teamId) {
		return teamService.findById(teamId);
	}

	@PostMapping
	public ResponseEntity saveTeam(@RequestBody TeamDTO teamDTO) {
		teamService.saveTeam(teamDTO);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/score")
	public ResponseEntity saveTeamScore(@RequestBody TeamScoreDTO teamScoreDTO) {
		teamService.saveTeamScore(teamScoreDTO);
		return ResponseEntity.ok().build();
	}

}
