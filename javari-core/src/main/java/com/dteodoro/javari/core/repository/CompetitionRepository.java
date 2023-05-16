package com.dteodoro.javari.core.repository;

import com.dteodoro.javari.core.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {

	Competition findByYear(int year);

}
