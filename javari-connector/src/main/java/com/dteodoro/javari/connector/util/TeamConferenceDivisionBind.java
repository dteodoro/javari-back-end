package com.dteodoro.javari.connector.util;

import com.dteodoro.javari.commons.enumeration.NFLConference;
import com.dteodoro.javari.commons.enumeration.NFLDivision;

import lombok.Getter;

@Getter
public enum TeamConferenceDivisionBind {

	NYJ(NFLConference.AFC, NFLDivision.EAST), BUF(NFLConference.AFC, NFLDivision.EAST),
	MIA(NFLConference.AFC, NFLDivision.EAST), NE(NFLConference.AFC, NFLDivision.EAST),

	BAL(NFLConference.AFC, NFLDivision.NORTH), CIN(NFLConference.AFC, NFLDivision.NORTH),
	CLE(NFLConference.AFC, NFLDivision.NORTH), PIT(NFLConference.AFC, NFLDivision.NORTH),

	HOU(NFLConference.AFC, NFLDivision.SOUTH), IND(NFLConference.AFC, NFLDivision.SOUTH),
	JAX(NFLConference.AFC, NFLDivision.SOUTH), TEN(NFLConference.AFC, NFLDivision.SOUTH),

	DEN(NFLConference.AFC, NFLDivision.WEST), KC(NFLConference.AFC, NFLDivision.WEST),
	LV(NFLConference.AFC, NFLDivision.WEST), LAC(NFLConference.AFC, NFLDivision.WEST),

	DAL(NFLConference.NFC, NFLDivision.EAST), NYG(NFLConference.NFC, NFLDivision.EAST),
	PHI(NFLConference.NFC, NFLDivision.EAST), WSH(NFLConference.NFC, NFLDivision.EAST),

	CHI(NFLConference.NFC, NFLDivision.NORTH), DET(NFLConference.NFC, NFLDivision.NORTH),
	GB(NFLConference.NFC, NFLDivision.NORTH), MIN(NFLConference.NFC, NFLDivision.NORTH),

	ATL(NFLConference.NFC, NFLDivision.SOUTH), CAR(NFLConference.NFC, NFLDivision.SOUTH),
	NO(NFLConference.NFC, NFLDivision.SOUTH), TB(NFLConference.NFC, NFLDivision.SOUTH),

	ARI(NFLConference.NFC, NFLDivision.WEST), LAR(NFLConference.NFC, NFLDivision.WEST),
	SF(NFLConference.NFC, NFLDivision.WEST), SEA(NFLConference.NFC, NFLDivision.WEST);

	private NFLConference conference;
	private NFLDivision division;

	TeamConferenceDivisionBind(NFLConference conference, NFLDivision division) {
		this.conference = conference;
		this.division = division;
	}

}
