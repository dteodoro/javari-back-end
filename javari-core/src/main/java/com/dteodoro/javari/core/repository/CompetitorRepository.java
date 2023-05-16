package com.dteodoro.javari.core.repository;

import java.util.UUID;

import com.dteodoro.javari.core.domain.Competitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitorRepository extends JpaRepository<Competitor, UUID>{

}
