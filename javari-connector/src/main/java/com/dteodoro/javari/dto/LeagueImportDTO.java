package com.dteodoro.javari.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LeagueImportDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<TeamDTO> teams;
	public List<TeamDTO> getTeams() {
		return teams;
	}
	public void setTeams(List<TeamDTO> teams) {
		this.teams = teams;
	}
	@Override
	public String toString() {
		return "LeagueDTO [teams=" + teams + "]";
	}
	
	
}
