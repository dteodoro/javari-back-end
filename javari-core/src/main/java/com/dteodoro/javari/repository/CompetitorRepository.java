package com.dteodoro.javari.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dteodoro.javari.domain.game.Competitor;

public interface CompetitorRepository extends JpaRepository<Competitor, UUID>{

}
