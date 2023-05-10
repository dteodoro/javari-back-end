package com.dteodoro.javari.dto;

import java.io.Serializable;

import com.dteodoro.javari.enumeration.HomeAway;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompetitorImportDTO  implements Serializable,ImportDataDTO{
	private static final long serialVersionUID = 1L;
	Integer order;
	HomeAway homeAway;
	boolean winner;
	TeamImportDTO team;
	Integer score;
	
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public HomeAway getHomeAway() {
		return homeAway;
	}
	public void setHomeAway(HomeAway homeAway) {
		this.homeAway = homeAway;
	}
	public boolean isWinner() {
		return winner;
	}
	public void setWinner(boolean winner) {
		this.winner = winner;
	}
	public TeamImportDTO getTeam() {
		return team;
	}
	public void setTeam(TeamImportDTO team) {
		this.team = team;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "CompetitorDTO [order=" + order + ", homeAway=" + homeAway + ", winner=" + winner + ", team=" + team
				+ ", score=" + score + "]";
	}

	@Override
	public CompetitorDTO toDomainDto() {
		return CompetitorDTO.builder()
				.position(this.getOrder().byteValue())
				.homeAway(this.getHomeAway())
				.winner(this.isWinner())
				.score(this.getScore().byteValue())
				.team(this.getTeam().toDomainDto())
				.build();
	}

}