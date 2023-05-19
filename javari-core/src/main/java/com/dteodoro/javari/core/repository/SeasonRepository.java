package com.dteodoro.javari.core.repository;

import java.util.Optional;
import java.util.UUID;

import com.dteodoro.javari.core.domain.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, UUID> {
	Optional<Season> findBySlugAndCompetitionYear(String seasonSlug, Integer competitionYear);
}
