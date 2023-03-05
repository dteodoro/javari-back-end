package com.dteodoro.javari.domain.game;

import java.util.List;
import java.util.UUID;

import com.dteodoro.javari.enumeration.SeasonType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Season {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)	private UUID id;
	private String slug;
	@Enumerated(EnumType.STRING)
	private SeasonType type;
	@ManyToOne
	private Competition competition;
	@OneToMany(mappedBy = "season")
	private List<Schedule> schedules;
	
}
