package com.dteodoro.javari.core.domain;

import java.util.List;
import java.util.UUID;

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
	@OneToMany(fetch = FetchType.EAGER)
	private List<SeasonCalendar> seasonCalendars;

}
