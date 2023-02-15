package com.dteodoro.javari.repository;

import com.dteodoro.javari.domain.bettor.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ScoreRepository extends JpaRepository<Score, UUID> {
    Optional<Score> findByBettorId(UUID bettorId);
}
