package com.dteodoro.javari.game.controller;

import com.dteodoro.javari.commons.dto.ScheduleBySeasonDTO;
import com.dteodoro.javari.commons.dto.ScheduleDTO;

import com.dteodoro.javari.game.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/game/schedules")
@RequiredArgsConstructor
public class ScheduleController {
	
	private final ScheduleService scheduleService;

	@GetMapping("/season/{year}/bettor/{bettorId}")
	public List<ScheduleBySeasonDTO> findLastGamesByBettorId(@PathVariable(name="bettorId",required = true) UUID bettorId,
													 @PathVariable(name="year",required = true) String year){
		return scheduleService.findAllSchedulesBySeasonAndBettorId(Integer.valueOf(year),bettorId);
	}
	
	@GetMapping
	public List<ScheduleDTO> findAll(@PageableDefault(size=20) Pageable pageable,
									 @RequestParam(name = "season", required = false) UUID seasonId,
									 @RequestParam(name = "week", required = false) UUID weekId,
									 @RequestParam(name = "bettor",required = true) UUID bettorId){
		return scheduleService.findAllSchedules(bettorId,seasonId,weekId);
	}

	@GetMapping("/season/{year}/team/{teamId}")
	public List<ScheduleBySeasonDTO> findByTeamId(@PathVariable(name ="year",required = true) String year,
												  @PathVariable(name="teamId",required = true) UUID teamId){

		return scheduleService.findByTeam(teamId,Integer.valueOf(year));
	}

	@PostMapping
	public ResponseEntity saveSchedule(@RequestBody @Valid ScheduleDTO scheduleDTO){
		scheduleService.saveOrUpdate(scheduleDTO);
		return ResponseEntity.ok().build();
	}
}
