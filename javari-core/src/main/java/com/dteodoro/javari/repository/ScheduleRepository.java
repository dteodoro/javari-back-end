package com.dteodoro.javari.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dteodoro.javari.entity.Schedule;
import com.dteodoro.javari.enumeration.SeasonType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ScheduleRepository extends PagingAndSortingRepository<Schedule, UUID> {

	Schedule findByCompetitionId(Long competitionId);
	
	Page<Schedule> findBySeasonTypeAndSeasonCompetitionYear(Pageable pageable, SeasonType seasonType, int year);

	Optional<Schedule> findById(UUID scheduleId);

	Schedule save(Schedule schedule);

}
