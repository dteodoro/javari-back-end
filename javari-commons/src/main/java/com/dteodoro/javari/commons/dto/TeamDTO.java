package com.dteodoro.javari.commons.dto;

import java.util.UUID;

import com.dteodoro.javari.commons.enumeration.NFLConference;
import com.dteodoro.javari.commons.enumeration.NFLDivision;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDTO implements DomainDTO {
	private UUID id;
	@NotNull
	private Long espnId;
	private String viewId;
	@NotBlank
	private String name;
	@NotBlank
	private String displayName;
	@NotBlank
	private String shortDisplayName;
	@NotBlank
	private String abbreviation;
	@NotBlank
	private String color;
	@NotBlank
	private String alternativeColor;
	private String logo;
	@NotNull
	private NFLConference conference;
	@NotNull
	private NFLDivision division;
	// team score
	private int scoreWins;
	private int scoreLosses;
	private int scoreTies;
	private double scoreWinPercentage;
	private String scoreHome;
	private String scoreRoad;
	private String scoreVersusDiv;
	private String scoreVersusConf;
	private Integer scorePointsFor;
	private Integer scorePointsAgainst;
	private String scorePointDifferential;
	private String scoreStreak;
	private String scoreScoreSummary;
	private Integer scoreSeasonYear;
	private String scoreSeasonName;

}
