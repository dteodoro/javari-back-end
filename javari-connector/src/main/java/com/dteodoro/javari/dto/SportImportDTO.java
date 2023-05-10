package com.dteodoro.javari.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SportImportDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<LeagueImportDTO> leagues;
	@Override
	public String toString() {
		return "SportDTO [leagues=" + leagues + "]";
	}
	public List<LeagueImportDTO> getLeagues() {
		return leagues;
	}
	public void setLeagues(List<LeagueImportDTO> leagues) {
		this.leagues = leagues;
	}
	
	
}
