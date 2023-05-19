package com.dteodoro.javari.core.domain;

import java.util.List;
import java.util.UUID;

import com.dteodoro.javari.commons.enumeration.SeasonType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Season {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private String label;
	private String slug;
	@ManyToOne
	private Competition competition;
	@OneToMany
	private List<SeasonCalendar> seasonCalendars;
	@OneToMany(mappedBy = "season")
	private List<Schedule> schedules;

}
