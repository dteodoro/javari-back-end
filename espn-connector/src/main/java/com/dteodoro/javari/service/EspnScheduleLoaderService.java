package com.dteodoro.javari.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dteodoro.javari.domain.ScheduleLoader;
import com.dteodoro.javari.domain.team.TeamService;
import com.dteodoro.javari.dto.CompetitorImportDTO;
import com.dteodoro.javari.dto.EventImportDTO;
import com.dteodoro.javari.dto.SeasonImportDTO;
import com.dteodoro.javari.entity.Competition;
import com.dteodoro.javari.entity.Competitor;
import com.dteodoro.javari.entity.Schedule;
import com.dteodoro.javari.entity.Season;
import com.dteodoro.javari.enumeration.HomeAway;
import com.dteodoro.javari.enumeration.ScheduleStatus;
import com.dteodoro.javari.enumeration.SeasonType;
import com.dteodoro.javari.util.NFLProviderAPI;
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
	private final ScheduleService scheduleService;
	private final SeasonService seasonService;
	private final TeamService teamService;
	private final CompetitionService competitionService;

	@Override
	public void load() {
		log.info("Loading NFL Schedule by ESPN API...");
		try {
			List<EventImportDTO> events = loadEvent();
			if (!events.isEmpty()) {
				List<Schedule> schedules = events.stream().map(this::convertToSchedule).toList();
				for (Schedule schedule : schedules) {
					Schedule currentSchedule = scheduleService.findByCompetitionId(schedule.getCompetitionId());
					if (currentSchedule != null && !currentSchedule.getStatus().equals(schedule.getStatus())) {
						updateSchedule(currentSchedule, schedule);
					} else if(currentSchedule == null){
						scheduleService.save(schedule);
					}
				}
				log.info("Save Events ON DataBase :: " + events);
			} else {
				log.info("No results found");
			}
		} catch (Exception e) {
			log.error("Cannot load NFL Events by ESPN API", e);
		}

		log.info("NFL Teams load has finished");
	}

	private void updateSchedule(Schedule currentSchedule, Schedule schedule) {
		currentSchedule.setStatus(schedule.getStatus());
		currentSchedule.getHomeCompetitor().setWinner(schedule.getHomeCompetitor().getWinner());
		currentSchedule.getHomeCompetitor().setScore(schedule.getHomeCompetitor().getScore());
		currentSchedule.getAwayCompetitor().setWinner(schedule.getAwayCompetitor().getWinner());
		currentSchedule.getAwayCompetitor().setScore(schedule.getAwayCompetitor().getScore());
		scheduleService.update(currentSchedule);
	}

	private List<EventImportDTO> loadEvent() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		JsonNode jsonNodeResponse;
		try {
			jsonNodeResponse = restTemplate.getForObject(NFLProviderAPI.SCHEDULES_API.getUri(), JsonNode.class);
			JsonNode eventsNode = jsonNodeResponse != null ? jsonNodeResponse.get("events") : null;
			return mapper.readerForListOf(EventImportDTO.class).readValue(eventsNode);
		} catch (Exception e) {
			log.error("Cannot load Schedules from Schedule Provider API ");
			return Collections.emptyList();
		}

	}

	private Schedule convertToSchedule(EventImportDTO event) {
		Map<HomeAway, Competitor> competitors = event.getCompetitions().get(0).getCompetitors().stream()
				.map(this::convertToCompetitor).collect(Collectors.toMap(Competitor::getHomeAway, Function.identity()));
		
		return Schedule.builder().competitionId(Long.valueOf(event.getCompetitions().get(0).getId()))
				.name(event.getName())
				.shortName(event.getShortName())
				.startDate(event.getCompetitions().get(0).getStartDate())
				.season(findOrCreateSeason(event.getSeason()))
				.status(ScheduleStatus.valueOf(event.getStatus().getType().getName()))
				.homeCompetitor(competitors.get(HomeAway.HOME))
				.awayCompetitor(competitors.get(HomeAway.AWAY))
				.build();
	}

	private Season findOrCreateSeason(SeasonImportDTO seasonDto) {
		Season season = seasonService.findBySlug(seasonDto.getSlug());
		if(season == null) {
			season = new Season();
			season.setSlug(seasonDto.getSlug());
			season.setType(SeasonType.of(seasonDto.getType()));
			season.setCompetition(findOrCreateCompetition(seasonDto.getYear()));
			return seasonService.create(season);
		}
		return season;
	}

	private Competition findOrCreateCompetition(Integer year) {
		Competition competition = competitionService.findByYear(year);
		if(competition == null)
			return competitionService.create(year);
		return competition;
	}

	private Competitor convertToCompetitor(CompetitorImportDTO competitorDto) {
		try {
			return Competitor.builder()
					.position(competitorDto.getOrder().byteValue())
					.score(competitorDto.getScore().byteValue())
					.homeAway(competitorDto.getHomeAway())
					.winner(competitorDto.isWinner())
					.team(teamService.findByEspnId(Long.valueOf(competitorDto.getTeam().getEspnId())))
					.build();
		} catch (NumberFormatException e) {
			log.error("convertToCompetitor: cannot convert espnId");
		}
		return null;
	}

}
