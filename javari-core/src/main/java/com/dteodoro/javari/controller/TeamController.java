package com.dteodoro.javari.controller;

import java.util.List;
import java.util.UUID;

import com.dteodoro.javari.dto.ConferenceTeamsDTO;
import com.dteodoro.javari.enumeration.NFLConference;
import com.dteodoro.javari.enumeration.NFLDivision;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.dteodoro.javari.domain.team.TeamService;
import com.dteodoro.javari.dto.TeamDTO;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "${javari.client.front}")
@RestController
@RequestMapping("/teams")
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

}
