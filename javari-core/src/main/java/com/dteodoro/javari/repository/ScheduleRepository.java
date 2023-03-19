package com.dteodoro.javari.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.dteodoro.javari.domain.game.Schedule;
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

	List<Schedule> findBySeasonSlugAndSeasonCompetitionYear(String slug, Integer year);

	List<Schedule> findByHomeCompetitorTeamIdOrAwayCompetitorTeamId(UUID teamId, UUID teamIdCopy);
}
