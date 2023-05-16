package com.dteodoro.javari.connector.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dteodoro.javari.connector.domain.TeamLoader;
import com.dteodoro.javari.connector.dto.TeamImportDTO;
import com.dteodoro.javari.connector.util.NFLProviderAPI;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EspnTeamLoaderService implements TeamLoader {
	
	private final RestTemplate restTemplate;

	@Override
	public List<TeamImportDTO> load() {
		log.info("Loading NFL Team by ESPN API...");
		ObjectMapper objMapper = new ObjectMapper();
		JsonNode jsonNode = restTemplate.getForObject(NFLProviderAPI.TEAMS_API.getUri(), JsonNode.class);
		JsonNode teamsNode = jsonNode != null ? jsonNode.get("sports").path(0).path("leagues").path(0).path("teams") : null;
		List<TeamImportDTO> teamsImported = new ArrayList<>();
		for (JsonNode teamNode : teamsNode) {			
			try {
				TeamImportDTO teamDto = objMapper.readerFor(TeamImportDTO.class).readValue(teamNode.get("team"));
				if(teamDto != null){
					teamsImported.add(teamDto);
				}
			} catch (IOException e) {
				log.error("Cannot load NFL teams by ESPN API");
			}
		}
		log.info("Quantity of teams loaded: {0}",teamsImported.size());
		log.info("NFL Teams load has finished");
		return teamsImported;
	}

}
