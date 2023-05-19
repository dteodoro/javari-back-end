package com.dteodoro.javari.connector.http;

import com.dteodoro.javari.commons.dto.ScheduleDTO;
import com.dteodoro.javari.commons.dto.SeasonDTO;
import com.dteodoro.javari.commons.dto.TeamDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@FeignClient("javari-game-service")
public interface GameClient {
	@PostMapping(value = "/api/v1/game/teams", consumes = MediaType.APPLICATION_JSON_VALUE)
	void saveTeam(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @RequestBody TeamDTO teamDTO);

	@PostMapping(value = "/api/v1/game/schedules", consumes = MediaType.APPLICATION_JSON_VALUE)
	void saveSchedule(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @RequestBody ScheduleDTO scheduleDTO);

	@PostMapping(value = "/api/v1/game/seasons", consumes = MediaType.APPLICATION_JSON_VALUE)
	void saveSeason(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @RequestBody SeasonDTO seasonDTO);

}
