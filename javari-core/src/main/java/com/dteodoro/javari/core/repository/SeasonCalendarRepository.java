package com.dteodoro.javari.core.repository;

import com.dteodoro.javari.core.domain.SeasonCalendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeasonCalendarRepository extends JpaRepository<SeasonCalendar, UUID> {
	SeasonCalendar findByWeekAndSeasonSlugAndSeasonCompetitionYear(Integer week, String seasonSlug, Integer competitionYear);
}
