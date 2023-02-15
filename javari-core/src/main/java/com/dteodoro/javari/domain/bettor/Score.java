package com.dteodoro.javari.domain.bettor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import com.dteodoro.javari.domain.bet.Bet;
import com.dteodoro.javari.domain.bet.BetEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Score implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@OneToOne
	private Bettor bettor;
	private Integer points;
	private Integer amountBetMade;
	private Integer numberOfHits;
	private BigDecimal efficiencyPercentage;

	public Score(UUID bettorId, BetEnum bet, int i, int i1) {
	}

	public Score(Bettor bettor, Bet bet) {
		this.bettor = bettor;
		this.numberOfHits = bet.getWin() ? 1 : 0;
		this.efficiencyPercentage = BigDecimal.ZERO;
		this.amountBetMade = 0;
		this.points = bet.getScore();
	}
}
