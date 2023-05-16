package com.dteodoro.javari.commons.dto;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class ScoreDTO {

	private Integer points;
	private Integer amountBetMade;
	private Integer numberOfHits;
	private BigDecimal efficiencyPercentage;
	
}
