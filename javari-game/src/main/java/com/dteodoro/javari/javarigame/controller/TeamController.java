package com.dteodoro.javari.javarigame.controller;

import com.dteodoro.javari.javaricore.domain.Team;
import com.dteodoro.javari.dto.ConferenceTeamsDTO;
import com.dteodoro.javari.dto.TeamDTO;
import com.dteodoro.javari.enumeration.NFLConference;
import com.dteodoro.javari.enumeration.NFLDivision;
import com.dteodoro.javari.javarigame.service.TeamService;
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
											@RequestParam(name="division") String division) {

		return teamService.findByTypes(StringUtils.hasText(conference) ? NFLConference.valueOf(conference) : null ,
				StringUtils.hasText(division) ? NFLDivision.valueOf(division):null);
	}
	
	@GetMapping("/{teamId}")
	public TeamDTO findById(@PathVariable(name="teamId",required = true) UUID teamId) {
		return teamService.findById(teamId);
	}

	@PostMapping
	public ResponseEntity saveSchedule(@RequestBody TeamDTO teamDTO){
		//TODO implements save team
		teamService.saveTeam(new Team());
		return ResponseEntity.ok().build();
	}

}
