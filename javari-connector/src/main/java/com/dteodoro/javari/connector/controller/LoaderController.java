package com.dteodoro.javari.connector.controller;

import com.dteodoro.javari.connector.service.LoaderService;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/connector/loader")
@RequiredArgsConstructor
@Slf4j
public class LoaderController {

	private final LoaderService loaderService;

	@GetMapping("/teams")
	public void loadTeams() {
		log.info("Loading NFL Teams by ESPN API...");
		try {
			loaderService.loadTeams();
		} catch (Exception e) {
			log.error("Cannot Load NFL Teams",e);
		}
		log.info("NFL Team loading finished");
	}

	@GetMapping("/schedules")
	public void loadSchedules() {
		log.info("Loading Schedule by ESPN API...");
		try {
			loaderService.loadSchedules();
			loaderService.loadStandings();
		} catch (Exception e) {
			log.error("Cannot Load NFL Schedules",e);
		}
		log.info("NFL Schedule loading finished");
	}

	@GetMapping("/seasons")
	public void loadSeasons() {
		log.info("Loading Seasons by ESPN API...");
		try {
			loaderService.loadSeasons();
		} catch (Exception e) {
			log.error("Cannot Load NFL Seasons",e);
		}
		log.info("NFL Seasons loading finished");
	}
}
