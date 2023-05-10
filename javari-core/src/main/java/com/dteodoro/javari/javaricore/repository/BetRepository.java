package com.dteodoro.javari.javaricore.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dteodoro.javari.javaricore.domain.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepository extends JpaRepository<Bet, UUID>{

	Optional<List<Bet>> findByBettorId(UUID bettorId);

	Optional<Bet> findByScheduleId(UUID scheduleId);

}
