package com.dteodoro.javari.javaricore.repository;

import java.util.UUID;

import com.dteodoro.javari.javaricore.domain.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season, UUID> {

	Season findBySlug(String seasonSlug);
}
