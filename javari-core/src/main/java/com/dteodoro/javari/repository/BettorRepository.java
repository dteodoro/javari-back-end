package com.dteodoro.javari.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dteodoro.javari.domain.bettor.Bettor;

public interface BettorRepository extends JpaRepository<Bettor, UUID> {
	Optional<Bettor> findByUsername(String username);
}
