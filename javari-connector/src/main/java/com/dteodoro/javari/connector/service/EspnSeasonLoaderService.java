package com.dteodoro.javari.connector.service;

import com.dteodoro.javari.connector.domain.SeasonLoader;
import com.dteodoro.javari.connector.dto.SeasonImportDTO;
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
@Slf4j
@RequiredArgsConstructor
@Service
public class EspnSeasonLoaderService implements SeasonLoader {

	private final RestTemplate restTemplate;
	@Override
	public List<SeasonImportDTO> load() {
		log.info("Loading NFL Seasons by ESPN API...");
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			JsonNode jsonNodeResponse = restTemplate.getForObject(NFLProviderAPI.SCHEDULES_API.getUri(), JsonNode.class);
			JsonNode seasonNode = jsonNodeResponse != null ? jsonNodeResponse.get("leagues").path(0).path("calendar") : null;

			return mapper.readerForListOf(SeasonImportDTO.class).readValue(seasonNode);
		} catch (Exception e) {
			log.error("Cannot load Seasons from Seasons Provider API ");
			return Collections.emptyList();
		}
	}
}
