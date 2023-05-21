package com.dteodoro.javari.core.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.dteodoro.javari.commons.enumeration.ScheduleStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

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

	@OneToOne(fetch = FetchType.EAGER)
	private SeasonCalendar seasonCalendar;

	@Enumerated(EnumType.STRING)
	private ScheduleStatus status;
	
	@OneToOne
	private Competitor homeCompetitor;
	
	@OneToOne
	private Competitor awayCompetitor;

	@OneToMany(mappedBy = "schedule", fetch = FetchType.EAGER)
	private List<Bet> bets;

}
