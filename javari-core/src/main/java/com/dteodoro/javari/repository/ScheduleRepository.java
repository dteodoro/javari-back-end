package com.dteodoro.javari.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dteodoro.javari.domain.game.Schedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, UUID> {

	Schedule findByCompetitionId(Long competitionId);

	Optional<Schedule> findById(UUID scheduleId);

	Schedule save(Schedule schedule);

	@Query("select distinct c.year from Competition c")
    List<String> findFilterMenuYear();
	@Query("select distinct s.slug from Season s")
	List<String> findFilterMenuSeason();

	Page<Schedule> findBySeasonSlugAndSeasonCompetitionYear(Pageable pageable, String slug, Integer year);
}
