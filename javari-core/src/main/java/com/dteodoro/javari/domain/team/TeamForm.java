package com.dteodoro.javari.domain.team;

import com.dteodoro.javari.enumeration.NFLConference;
import com.dteodoro.javari.enumeration.NFLDivision;

import lombok.Data;

@Data
public class TeamForm {
	
	private NFLConference conference;
	private NFLDivision division;

}
