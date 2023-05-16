package com.dteodoro.javari.game.service;

import com.dteodoro.javari.core.domain.Season;
import com.dteodoro.javari.core.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SeasonService {
	
	private final SeasonRepository seasonRepo;

	public Season findBySlug(String seasonSlug) {
		return seasonRepo.findBySlug(seasonSlug);
	}

	public Season create(Season season) {
		return seasonRepo.save(season);
	}

}
