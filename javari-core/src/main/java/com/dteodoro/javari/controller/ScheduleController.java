package com.dteodoro.javari.controller;

import java.util.UUID;

import com.dteodoro.javari.dto.ScheduleFilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dteodoro.javari.dto.ScheduleDTO;
import com.dteodoro.javari.enumeration.SeasonType;
import com.dteodoro.javari.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "${javari.client.front}")
@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {
	
	private final ScheduleService scheduleService;
	
	@GetMapping("/session/{year}/{slug}/bettor/{bettorId}")
	@CrossOrigin(origins = "${javari.client.front}")
	public Page<ScheduleDTO> findAll(@PageableDefault(size=20) Pageable pageable,
									 @PathVariable(name = "year", required = false) String year,
									 @PathVariable(name = "seasonType", required = false) String slug,
									 @PathVariable(name = "bettorId",required = true) UUID bettorId){
		if(!StringUtils.hasText(year)) {
			return scheduleService.findBySeason(Integer.valueOf(year),slug,bettorId, pageable);
		}
		return scheduleService.findAll(bettorId,pageable);
	}

	@GetMapping("/filters")
	public ScheduleFilterDTO getScheduleFilters(){
		return scheduleService.getScheduleFilters();
	}
	
	
}
