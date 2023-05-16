package com.dteodoro.javari.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
public class Score implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@OneToOne(fetch = FetchType.LAZY,optional = false)
	private Bettor bettor;
	private Integer points;
	private Integer amountBetMade;
	private Integer numberOfHits;
	private BigDecimal efficiencyPercentage;

	public Score(){
		this.numberOfHits = 0;
		this.efficiencyPercentage = BigDecimal.ZERO;
		this.amountBetMade = 0;
		this.points = 0;
	}

	public Score(Bettor bettor, Bet bet) {
		this.bettor = bettor;
		this.numberOfHits = bet.getWin() ? 1 : 0;
		this.efficiencyPercentage = BigDecimal.ZERO;
		this.amountBetMade = 0;
		this.points = bet.getScore();
	}
	public Score(Bettor bettor){
		this.bettor = bettor;
		this.numberOfHits = 0;
		this.efficiencyPercentage = BigDecimal.ZERO;
		this.amountBetMade = 0;
		this.points = 0;
	}
}
