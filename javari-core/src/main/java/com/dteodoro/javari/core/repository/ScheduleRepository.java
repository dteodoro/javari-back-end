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

	List<Schedule> findByHomeCompetitorTeamIdOrAwayCompetitorTeamIdAndSeasonCalendarSeasonCompetitionYear( UUID teamId, UUID teamId1, Integer year);

	@Query("select s from Schedule s join fetch SeasonCalendar sc on s.seasonCalendar.id = sc.id " +
			" join fetch Season season on sc.season.id = season.id where season.id = :seasonId " +
	        " order by s.startDate ")
	List<Schedule> findBySeasonId(UUID seasonId);

	@Query("select s from Schedule s join fetch SeasonCalendar sc on s.seasonCalendar.id = sc.id " +
			" join fetch Season season on sc.season.id = season.id where season.id = :seasonId and sc.id = :weekId " +
			" order by s.startDate ")
	List<Schedule> findBySeasonIdAndWeekId(UUID seasonId,UUID weekId);

	@Query("select s from Schedule s order by s.startDate, s.status asc ")
	List<Schedule> findAllOrderByStartDate();
}
