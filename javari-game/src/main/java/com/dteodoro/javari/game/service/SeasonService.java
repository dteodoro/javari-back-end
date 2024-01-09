package com.dteodoro.javari.game.service;

import com.dteodoro.javari.commons.dto.SeasonCalendarDTO;
import com.dteodoro.javari.commons.dto.SeasonDTO;
import com.dteodoro.javari.commons.dto.SeasonFilterDTO;
import com.dteodoro.javari.commons.dto.WeekFilterDTO;
import com.dteodoro.javari.core.domain.Season;
import com.dteodoro.javari.core.domain.SeasonCalendar;
import com.dteodoro.javari.core.repository.SeasonCalendarRepository;
import com.dteodoro.javari.core.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SeasonService {

	private final SeasonRepository seasonRepo;
	private final SeasonCalendarRepository seasonCalendarRepo;
	private final CompetitionService competitionService;
	private final ModelMapper mapper;

	public Season create(Season season) {
		return seasonRepo.save(season);
	}

	public void save(SeasonDTO seasonDTO) {
		Season currentSeason = seasonRepo
				.findByLabelAndCompetitionYear(seasonDTO.getSlug(), seasonDTO.getCompetitionYear()).orElse(null);
		if (currentSeason == null) {
			Season seasonSaved = create(convetToSeason(seasonDTO));
			createSeasonCalendar(seasonDTO.getSeasonCalendars(), seasonSaved);
		}
	}

	private Season convetToSeason(SeasonDTO seasonDTO) {
		Season season = new Season();
		season.setCompetition(competitionService.findByYear(seasonDTO.getCompetitionYear()));
		season.setLabel(seasonDTO.getLabel());
		season.setSlug(seasonDTO.getSlug());
		return season;
	}

	private void createSeasonCalendar(List<SeasonCalendarDTO> seasonCalendars, Season season) {
		seasonCalendars.stream()
				.map(dto -> mapper.map(dto, SeasonCalendar.class))
				.forEach(s -> {
					s.setSeason(season);
					seasonCalendarRepo.save(s);
				});
	}

	public void update(Season season) {
		seasonRepo.save(season);
	}

	public void updateCalendar(SeasonCalendar seasonCalendar) {
		seasonCalendarRepo.save(seasonCalendar);
	}

	public SeasonCalendar findByWeekAndSeasonSlugAndSeasonCompetitionYear(Integer week, String seasonSlug,
			Integer competitionYear) {
		return seasonCalendarRepo.findByWeekAndSeasonSlugAndSeasonCompetitionYear(week, seasonSlug, competitionYear);
	}

	public List<SeasonFilterDTO> getSeasonFilters() {
		List<SeasonFilterDTO> seasonFilters = new ArrayList<>();
		List<SeasonCalendar> seasonsCalendar = seasonCalendarRepo
				.findSeasonCalendarBySeasonCompetitionYearOrderByStartDate(2023);
		Map<Season, List<SeasonCalendar>> seasons = seasonsCalendar.stream()
				.collect(Collectors.groupingBy(sc -> sc.getSeason()));
		seasons.forEach((season, weeks) -> seasonFilters.add(
				SeasonFilterDTO.builder()
						.seasonId(season.getId())
						.seasonLabel(season.getLabel())
						.weeks(weeks.stream().map(this::convertToWeekFilterDTO).toList())
						.build()));
		return seasonFilters;
	}

	private WeekFilterDTO convertToWeekFilterDTO(SeasonCalendar seasonCalendar) {
		return new WeekFilterDTO(seasonCalendar.getId(), seasonCalendar.getLabel());
	}
}
