package com.dteodoro.javari.game.controller;

import com.dteodoro.javari.commons.dto.ScheduleDTO;
import com.dteodoro.javari.commons.dto.ScheduleFilterDTO;

import com.dteodoro.javari.game.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/game/schedules")
@RequiredArgsConstructor
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	
	@GetMapping("/session/{year}/{slug}/bettor/{bettorId}")
	public List<ScheduleDTO> findAll(@PageableDefault(size=20) Pageable pageable,
									 @PathVariable(name = "year", required = false) String year,
									 @PathVariable(name = "seasonType", required = false) String slug,
									 @PathVariable(name = "bettorId",required = true) UUID bettorId){
		if(!StringUtils.hasText(year)) {
			return scheduleService.findBySeason(Integer.valueOf(year),slug,bettorId);
		}
		return scheduleService.findAll(bettorId);
	}

	@GetMapping("/filters")
	public ScheduleFilterDTO getScheduleFilters(){
		return scheduleService.getScheduleFilters();
	}
	
	@GetMapping("/session/{year}/{slug}/team/{teamId}")
	public List<ScheduleDTO> findByTeamId(@PathVariable(name ="year",required = true) String year,
										  @PathVariable(name="slug",required = true) String slug,
										  @PathVariable(name="teamId",required = true) UUID teamId){

		return scheduleService.findByTeam(teamId,year,slug);
	}

	@PostMapping
	public ResponseEntity saveSchedule(@RequestBody @Valid ScheduleDTO scheduleDTO){
		scheduleService.saveOrUpdate(scheduleDTO);
		return ResponseEntity.ok().build();
	}
}
