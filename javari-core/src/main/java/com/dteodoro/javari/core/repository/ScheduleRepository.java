package com.dteodoro.javari.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dteodoro.javari.commons.enumeration.ScheduleStatus;
import com.dteodoro.javari.core.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {

	Schedule findByCompetitionId(Long competitionId);

	Optional<Schedule> findById(UUID scheduleId);

	Schedule save(Schedule schedule);

	@Query("select distinct c.year from Competition c")
    List<String> findFilterMenuYear();
	@Query("select distinct s.slug from Season s")
	List<String> findFilterMenuSeason();
	List<Schedule> findByHomeCompetitorTeamIdOrAwayCompetitorTeamIdAndSeasonCalendarSeasonCompetitionYear( UUID teamId, UUID teamId1, Integer year);

}
