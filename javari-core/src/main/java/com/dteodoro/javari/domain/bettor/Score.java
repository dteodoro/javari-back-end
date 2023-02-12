package com.dteodoro.javari.domain.bettor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
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

}
