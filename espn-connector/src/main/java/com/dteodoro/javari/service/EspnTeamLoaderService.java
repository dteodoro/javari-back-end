package com.dteodoro.javari.service;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dteodoro.javari.domain.TeamLoader;
import com.dteodoro.javari.domain.team.Team;
import com.dteodoro.javari.domain.team.TeamService;
import com.dteodoro.javari.dto.TeamImportDTO;
import com.dteodoro.javari.util.NFLProviderAPI;
import com.dteodoro.javari.util.TeamConferenceDivisionBind;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EspnTeamLoaderService implements TeamLoader {
	
	private final RestTemplate restTemplate;
	private final TeamService teamService;
	private final ModelMapper modelMapper;

	@Override
	public void load() {
		log.info("Loading NFL Team by ESPN API...");
		ObjectMapper objMapper = new ObjectMapper();
		JsonNode jsonNode = restTemplate.getForObject(NFLProviderAPI.TEAMS_API.getUri(), JsonNode.class);
		JsonNode teamsNode = jsonNode != null ? jsonNode.get("sports").path(0).path("leagues").path(0).path("teams") : null;
		for (JsonNode teamNode : teamsNode) {			
			try {
				TeamImportDTO teamDto;
				teamDto = objMapper.readerFor(TeamImportDTO.class).readValue(teamNode.get("team"));
				if(teamDto != null) {
					Team team = modelMapper.map(teamDto,Team.class);
					team.setLogo(teamDto.getLogos().get(0).getHref());
					team.setConference(TeamConferenceDivisionBind.valueOf(teamDto.getAbbreviation()).getConference());
					team.setDivision(TeamConferenceDivisionBind.valueOf(teamDto.getAbbreviation()).getDivision());
					teamService.saveTeam(team);
				}
			} catch (IOException e) {
				log.error("Cannot load NFL teams by ESPN API");
			}
		}
		log.info("NFL Teams load has finished");
	}

}
