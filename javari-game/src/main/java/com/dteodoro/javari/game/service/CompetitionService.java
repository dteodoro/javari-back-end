package com.dteodoro.javari.game.service;

import com.dteodoro.javari.core.domain.Competition;
import com.dteodoro.javari.core.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetitionService {
	
	private final CompetitionRepository competitionRepo;
	
	public Competition findByYear(int year) {
		return competitionRepo.findByYear(year);
		
	}

	public Competition create(Integer year) {
		Competition competition = new Competition();
		competition.setYear(year);
		return competitionRepo.save(competition);
	}
}
