package com.dteodoro.javari.dto;

import java.util.UUID;

import com.dteodoro.javari.domain.bet.BetEnum;

import lombok.Data;

@Data
public class BetDTO {

	private UUID id;
	private UUID bettorId;
	private UUID scheduleId;
	private BetEnum bet;
	private Boolean win;

	private Integer score;
	
}
