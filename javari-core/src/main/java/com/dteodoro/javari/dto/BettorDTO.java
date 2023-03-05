package com.dteodoro.javari.dto;

import lombok.Data;

@Data
public class BettorDTO {

	private String bettorId;
	private String nickName;
	private Integer currentPosition;
	private Integer previousPosition;
	private TeamDTO favoriteTeam;
	private ScoreDTO score;
}
