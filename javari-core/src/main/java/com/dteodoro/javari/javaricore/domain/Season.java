package com.dteodoro.javari.javaricore.domain;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Season {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)	private UUID id;
	private String slug;
	@Enumerated(EnumType.STRING)
	private SeasonType type;
	@ManyToOne
	private Competition competition;
	@OneToMany(mappedBy = "season")
	private List<Schedule> schedules;

	public Season(String slug){
		this.slug = slug;
	}
	
}
