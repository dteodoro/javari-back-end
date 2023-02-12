package com.dteodoro.javari.service;

import org.springframework.stereotype.Service;

import com.dteodoro.javari.entity.Competition;
import com.dteodoro.javari.repository.CompetitionRepository;

import lombok.RequiredArgsConstructor;

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
