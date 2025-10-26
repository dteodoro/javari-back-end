package com.dteodoro.javari.core.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.util.StringUtils;

@Data
@Entity
public class TeamScore {
	@Id @GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private int wins;
	private int losses;
	private int ties;
	private double winPercentage;
    private double winDivPercentage;
    private double winConfPercentage;
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
    private double strengthOfVictory;

    public void setVersusDiv(String versusDiv) {
        this.versusDiv = versusDiv;
        this.winDivPercentage = calculatePercentage(versusDiv);
    }
    public void setVersusConf(String versusConf) {
        this.versusConf = versusConf;
        this.winConfPercentage = calculatePercentage(versusConf);
    }

    private double calculatePercentage(String scoreText) {
        if(StringUtils.hasText(scoreText)){
            try{
                String[] parts = scoreText.split("-");
                int wins = Integer.parseInt(parts[0].trim());
                int losses = Integer.parseInt(parts[1].trim());
                int ties = 0;
                if(parts.length > 2){
                    ties = Integer.parseInt(parts[2].trim());
                }
                int totalGames = wins + losses + ties;
                if(totalGames > 0){
                    return (wins + (ties * 0.5)) / totalGames;
                }
            } catch (NumberFormatException e){
                return 0.0;
            }
        }
        return 0.0;
    }

	public TeamScore() {
		updateScoreSummary();
	}

	public void updateScoreSummary(){
		scoreSummary = wins+" - "+losses+" - "+ties;
	}

}
