package com.dteodoro.javari.core.domain;

import java.util.UUID;

import com.dteodoro.javari.commons.enumeration.BetEnum;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Bet {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	@Column(nullable = false)
	private UUID bettorId;
	@ManyToOne
	private Schedule schedule;
	@Enumerated(EnumType.STRING)
	private BetEnum bet;
	private Integer score;
	private Boolean win;

	public Bet(final UUID bettorId) {
		this.bettorId = bettorId;
	}

	@Override
	public boolean equals(final Object obj) {
		if(obj==null){
			return false;
		}
		return this.bettorId.equals(((Bet)obj).getBettorId());
	}
}
