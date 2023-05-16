package com.dteodoro.javari.core.domain;

import java.util.UUID;

import com.dteodoro.javari.commons.enumeration.BetEnum;
import jakarta.persistence.*;

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
