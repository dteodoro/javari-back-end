package com.dteodoro.javari.core.repository;

import com.dteodoro.javari.core.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {

	Optional<Competition> findByYear(int year);

}
