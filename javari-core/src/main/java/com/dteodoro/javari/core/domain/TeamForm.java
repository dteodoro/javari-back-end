package com.dteodoro.javari.core.domain;

import com.dteodoro.javari.commons.enumeration.NFLConference;
import com.dteodoro.javari.commons.enumeration.NFLDivision;

import lombok.Data;

@Data
public class TeamForm {
	
	private NFLConference conference;
	private NFLDivision division;

}
