package com.dteodoro.javari.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dteodoro.javari.domain.game.Season;

public interface SeasonRepository extends JpaRepository<Season, UUID> {

	Season findBySlug(String seasonSlug);
}
