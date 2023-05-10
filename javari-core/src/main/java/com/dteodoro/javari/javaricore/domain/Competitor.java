package com.dteodoro.javari.javaricore.domain;

import java.util.UUID;

import com.dteodoro.javari.enumeration.HomeAway;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Competitor {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	private byte position;
	private byte score;
	@Enumerated(EnumType.STRING)
	private HomeAway homeAway;
	private Boolean winner;
	@ManyToOne
	private Team team;
	
}
