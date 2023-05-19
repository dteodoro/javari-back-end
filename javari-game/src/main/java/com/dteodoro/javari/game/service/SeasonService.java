package com.dteodoro.javari.game.service;

import com.dteodoro.javari.commons.dto.SeasonCalendarDTO;
import com.dteodoro.javari.commons.dto.SeasonDTO;
import com.dteodoro.javari.core.domain.Season;
import com.dteodoro.javari.core.domain.SeasonCalendar;
import com.dteodoro.javari.core.repository.SeasonCalendarRepository;
import com.dteodoro.javari.core.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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

	public Season findBySlugAndCompetitionYear(String seasonSlug, Integer competitionYear) {
		return seasonRepo.findBySlugAndCompetitionYear(seasonSlug,competitionYear).orElse(null);
	}

	public void save(SeasonDTO seasonDTO) {
		Season currentSeason = findBySlugAndCompetitionYear(seasonDTO.getSlug(),seasonDTO.getCompetitionYear());
		if(currentSeason == null){
			Season seasonSaved = create(convetToSeason(seasonDTO));
			createSeasonCalendar(seasonDTO.getSeasonCalendars(),seasonSaved);
		}
	}

	private Season convetToSeason(SeasonDTO seasonDTO) {
		Season season = new Season();
		season.setCompetition(competitionService.findByYear(seasonDTO.getCompetitionYear()));
		season.setLabel(seasonDTO.getLabel());
		season.setSlug(seasonDTO.getSlug());
		return season;
	}

	private void createSeasonCalendar(List<SeasonCalendarDTO> seasonCalendars,Season season) {
		seasonCalendars.stream()
				.map(dto -> mapper.map(dto, SeasonCalendar.class))
				.forEach(s -> {
					s.setSeason(season);
					seasonCalendarRepo.save(s);
				});
	}

}
