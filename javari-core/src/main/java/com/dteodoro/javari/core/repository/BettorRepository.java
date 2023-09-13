package com.dteodoro.javari.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dteodoro.javari.core.domain.Bettor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BettorRepository extends JpaRepository<Bettor, UUID> {
	Optional<Bettor> findByEmail(String username);

	List<Bettor> findAllByOrderByCurrentPositionAsc();

	List<Bettor> findAllByOrderByScorePointsDesc();
}
