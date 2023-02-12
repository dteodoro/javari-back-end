package com.dteodoro.javari.service;

import org.springframework.stereotype.Service;

import com.dteodoro.javari.entity.Season;
import com.dteodoro.javari.repository.SeasonRepository;

import lombok.RequiredArgsConstructor;

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
