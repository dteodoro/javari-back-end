package com.dteodoro.javari.connector.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.dteodoro.javari.connector.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dteodoro.javari.connector.domain.ScheduleLoader;
import com.dteodoro.javari.commons.enumeration.HomeAway;
import com.dteodoro.javari.connector.util.NFLProviderAPI;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EspnScheduleLoaderService implements ScheduleLoader {

	private final RestTemplate restTemplate;

	@Override
	public List<ScheduleImportDTO> load() {
		log.info("Loading NFL Schedule by ESPN API...");
		try {
			List<EventImportDTO> events = loadEvent();
			log.info("Quantity of events loaded {0}", events.size());
			if (!events.isEmpty()) {
				return events.stream().map(this::convertToScheduleImportDto).toList();
			} else {
				log.info("No results found");
			}
		} catch (Exception e) {
			log.error("Cannot load NFL Events by ESPN API", e);
		}
		return null;
	}

	private List<EventImportDTO> loadEvent() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		JsonNode jsonNodeResponse;
		try {
			log.info("loading schedule data from ESPN API...");
			jsonNodeResponse = restTemplate.getForObject(NFLProviderAPI.SCHEDULES_API.getUri(), JsonNode.class);
			JsonNode eventsNode = jsonNodeResponse != null ? jsonNodeResponse.get("events") : null;
			log.info("schedule data from ESPN: " + eventsNode);
			return mapper.readerForListOf(EventImportDTO.class).readValue(eventsNode);
		} catch (Exception e) {
			log.error("Cannot load Schedules from Schedule Provider API ");
			return Collections.emptyList();
		}

	}

	private ScheduleImportDTO convertToScheduleImportDto(EventImportDTO event) {
		var competitors = event.getCompetitions().get(0).getCompetitors().stream()
				.collect(Collectors.toMap(CompetitorImportDTO::getHomeAway, Function.identity()));

		return ScheduleImportDTO.builder()
				.competitionId(Long.valueOf(event.getCompetitions().get(0).getId()))
				.name(event.getName())
				.shortName(event.getShortName())
				.startDate(event.getCompetitions().get(0).getStartDate())
				.season(event.getSeason())
				.week(event.getWeek().getNumber())
				.status(event.getStatus().getType().getName())
				.homeCompetitor(competitors.get(HomeAway.HOME))
				.awayCompetitor(competitors.get(HomeAway.AWAY))
				.build();
	}

}
