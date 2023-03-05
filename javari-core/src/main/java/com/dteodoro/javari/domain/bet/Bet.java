package com.dteodoro.javari.domain.bet;

import java.util.UUID;

import jakarta.persistence.*;

import com.dteodoro.javari.domain.game.Schedule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Bet {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@Column(nullable = false)
	private UUID bettorId;
	@ManyToOne
	private Schedule schedule;
	@Enumerated(EnumType.STRING)
	private BetEnum bet;
	private Integer score;
	private Boolean win;
	
}
