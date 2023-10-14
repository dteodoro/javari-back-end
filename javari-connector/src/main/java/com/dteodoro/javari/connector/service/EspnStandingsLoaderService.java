package com.dteodoro.javari.connector.service;

import com.dteodoro.javari.connector.domain.StandingLoader;
import com.dteodoro.javari.connector.dto.*;
import com.dteodoro.javari.connector.util.NFLProviderAPI;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class EspnStandingsLoaderService implements StandingLoader {

	private final RestTemplate restTemplate;
	@Override
	public List<StandingsTeamStatsImportDTO> load() {
		log.info("Loading NFL Standings by ESPN API...");
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			JsonNode jsonNodeResponse = restTemplate.getForObject(NFLProviderAPI.STANDINGS_API.getUri(), JsonNode.class);

			JsonNode seasonNode = jsonNodeResponse != null ? jsonNodeResponse.get("content").get("config")
																				 .get("seasons") : null;

			JsonNode standingsLeague = jsonNodeResponse != null ? jsonNodeResponse.get("content")
																			      .get("standings")
																				  .get("groups") : null;

			StandingsSeasonImportDTO season = mapper.readerFor(StandingsSeasonImportDTO.class).readValue(seasonNode);
			List<StandingsConferenceImportDTO> standingByConference =  mapper.readerForListOf(StandingsConferenceImportDTO.class).readValue(standingsLeague);

			return  standingByConference.stream()
										.flatMap(conf -> conf.getGroups().stream())
										.flatMap(div -> div.getStandings().getEntries().stream())
										.map(teamStatus -> {
											teamStatus.setSeasonYear(Integer.valueOf(season.getSeasonYear()));
											teamStatus.setSeasonName(season.getSeasonName());
											return teamStatus;})
										.collect(Collectors.toList());
		} catch (Exception e) {
			log.error("Cannot load Standings from Standings Provider API ");
			return Collections.emptyList();
		}
	}
}
