package com.dteodoro.javari.javaricore.repository;

import java.util.UUID;

import com.dteodoro.javari.javaricore.domain.Competitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitorRepository extends JpaRepository<Competitor, UUID>{

}
