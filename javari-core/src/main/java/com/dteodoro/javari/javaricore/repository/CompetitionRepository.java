package com.dteodoro.javari.javaricore.repository;

import com.dteodoro.javari.javaricore.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {

	Competition findByYear(int year);

}
