package com.dteodoro.javari.core.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
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
	private int winsOnConference;
	private String scoreSummary;

	public void updateScoreSummary(){
		scoreSummary = wins+" - "+losses+" - "+ties;
	}
	public void updateWinPercentage(){
		int totalMatch = wins + losses + ties;
		winPercentage = (double) (wins * 100)/totalMatch;
	}
}
