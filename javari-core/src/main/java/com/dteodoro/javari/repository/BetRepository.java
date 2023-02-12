package com.dteodoro.javari.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dteodoro.javari.domain.bet.Bet;

public interface BetRepository extends JpaRepository<Bet, UUID>{

	Optional<List<Bet>> findByBettorId(UUID bettorId);

	Optional<Bet> findByScheduleId(UUID scheduleId);

}
