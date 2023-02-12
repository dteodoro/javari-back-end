package com.dteodoro.javari.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.dteodoro.javari.domain.bet.Bet;
import com.dteodoro.javari.enumeration.ScheduleStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private Long competitionId;
	private String name;
	private String shortName;
	private LocalDateTime startDate;
	@ManyToOne
	private Season season;
	@Enumerated(EnumType.STRING)
	private ScheduleStatus status;
	
	@OneToOne
	private Competitor homeCompetitor;
	
	@OneToOne
	private Competitor awayCompetitor;

	@OneToMany(mappedBy = "schedule", fetch = FetchType.EAGER)
	private List<Bet> bets;

}
