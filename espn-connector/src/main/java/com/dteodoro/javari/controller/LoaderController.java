package com.dteodoro.javari.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dteodoro.javari.domain.ScheduleLoader;
import com.dteodoro.javari.domain.TeamLoader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "${javari.client.front}")
@RestController
@RequestMapping("/loader")
@RequiredArgsConstructor
@Slf4j
public class LoaderController {

	private final TeamLoader teamLoader;
	private final ScheduleLoader scheduleLoader;
	
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/teams")
	public void loadTeams() {
		log.info("Loading NFL Teams by ESPN API...");
		try {
			teamLoader.load();
		} catch (Exception e) {
			log.error("Cannot Load NFL Teams",e);
		}
		log.info("NFL Team loading finished");
	}
	
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/schedules")
	public void loadSchedules() {
		log.info("Loading Schedule by ESPN API...");
		scheduleLoader.load();
		log.info("NFL Schedule loading finished");
	}
	
}
