package com.dteodoro.javari.javaricore.repository;

import java.util.Optional;
import java.util.UUID;

import com.dteodoro.javari.javaricore.domain.Bettor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BettorRepository extends JpaRepository<Bettor, UUID> {
	Optional<Bettor> findByEmail(String username);
}
