package com.dteodoro.javari.game.service;

import com.dteodoro.javari.core.domain.Competition;
import com.dteodoro.javari.core.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CompetitionService {
	
	private final CompetitionRepository competitionRepo;
	
	public Competition findByYear(int year) {
		Competition competition = competitionRepo.findByYear(year).orElse(null);
		if(competition == null){
			Competition currentYear = competitionRepo.findByYear(LocalDate.now().getYear()).orElse(null);
			if(currentYear == null) {
				return create(LocalDate.now().getYear());
			}
			return currentYear;
		}
		return competition;
	}

	public Competition create(Integer year) {
		Competition competition = new Competition();
		competition.setYear(year);
		return competitionRepo.save(competition);
	}
}
