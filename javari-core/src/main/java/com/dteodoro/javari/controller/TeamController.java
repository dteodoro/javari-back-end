package com.dteodoro.javari.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dteodoro.javari.domain.team.TeamService;
import com.dteodoro.javari.dto.TeamDTO;
import com.dteodoro.javari.dto.TeamForm;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "${javari.client.front}")
@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

	private TeamService teamService;

	@GetMapping
	public Page<TeamDTO> findAll(@PageableDefault(direction = Direction.DESC) Pageable pageable, TeamForm teamForm) {
		return teamService.findAll(teamForm, pageable);
	}
	
	@GetMapping("/team/{teamId}")
	public TeamDTO findById(@PathVariable(name="teamId",required = true) UUID teamId) {
		return teamService.findById(teamId);
	}

}
