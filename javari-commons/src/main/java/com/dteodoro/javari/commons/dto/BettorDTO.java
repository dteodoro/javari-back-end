package com.dteodoro.javari.commons.dto;

import lombok.Data;

@Data
public class BettorDTO {

	private String bettorId;
	private String nickName;
	private Integer currentPosition;
	private Integer previousPosition;
	private TeamDTO favoriteTeam;
	private ScoreDTO score;
	private String image;
}
