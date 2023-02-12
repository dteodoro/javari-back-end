package com.dteodoro.javari.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dteodoro.javari.entity.Competition;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {

	Competition findByYear(int year);

}
