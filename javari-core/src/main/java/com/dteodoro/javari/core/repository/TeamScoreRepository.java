package com.dteodoro.javari.core.repository;

import com.dteodoro.javari.core.domain.TeamScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TeamScoreRepository extends JpaRepository<TeamScore, UUID> {
}
