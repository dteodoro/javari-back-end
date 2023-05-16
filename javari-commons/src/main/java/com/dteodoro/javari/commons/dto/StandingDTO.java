package com.dteodoro.javari.commons.dto;

import java.util.List;

import com.dteodoro.javari.commons.enumeration.NFLDivision;

import lombok.Data;

@Data
public class StandingDTO {
	
	private NFLDivision division;
	private List<TeamDTO> teams;
	
	public StandingDTO(NFLDivision division, List<TeamDTO> teams) {
		this.division = division;
		this.teams = teams;
	}

}
