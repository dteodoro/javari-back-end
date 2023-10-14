package com.dteodoro.javari.core.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class TeamScore {
	@Id @GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private int wins;
	private int losses;
	private int ties;
	private double winPercentage;
	private String home;
	private String road;
	private String versusDiv;
	private String versusConf;
	private Integer pointsFor;
	private Integer pointsAgainst;
	private String pointDifferential;
	private String streak;
	private String scoreSummary;
	private Integer seasonYear;
	private String seasonName;

	public void updateScoreSummary(){
		scoreSummary = wins+" - "+losses+" - "+ties;
	}

}
