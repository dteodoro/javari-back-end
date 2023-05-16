package com.dteodoro.javari.connector.dto;

import java.io.Serializable;
import java.util.List;

import com.dteodoro.javari.commons.dto.TeamDTO;
import com.dteodoro.javari.connector.util.TeamConferenceDivisionBind;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class TeamImportDTO implements Serializable,ImportDataDTO{

	private static final long serialVersionUID = 1L;
	TeamImportDTO team;
	@JsonProperty(value = "id")
	String espnId;
	String uid;
	String name;
	String abbreviation;
	String displayName;
	String shortDisplayName;
	String color;
	String alternateColor;
	String location;
	List<LogoImportDTO> logos;

	public TeamImportDTO getTeam() {
		return team;
	}


	public void setTeam(TeamImportDTO team) {
		this.team = team;
	}


	public String getEspnId() {
		return espnId;
	}


	public void setEspnId(String espnId) {
		this.espnId = espnId;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAbbreviation() {
		return abbreviation;
	}


	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	public String getShortDisplayName() {
		return shortDisplayName;
	}


	public void setShortDisplayName(String shortDisplayName) {
		this.shortDisplayName = shortDisplayName;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getAlternateColor() {
		return alternateColor;
	}


	public void setAlternateColor(String alternateColor) {
		this.alternateColor = alternateColor;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public List<LogoImportDTO> getLogos() {
		return logos;
	}


	public void setLogos(List<LogoImportDTO> logos) {
		this.logos = logos;
	}


	@Override
	public String toString() {
		return "TeamDTO [id=" + espnId + ", uid=" + uid + ", name=" + name + ", abbreviation="
				+ abbreviation + ", displayName=" + displayName + ", shortDisplayName=" + shortDisplayName + ", color="
				+ color + ", alternateColor=" + alternateColor + ", location=" + location + "]";
	}


	@Override
	public TeamDTO toDomainDto() {
		return TeamDTO.builder()
				.espnId(Long.valueOf(this.espnId))
				.viewId((this.uid))
				.name(this.name)
				.displayName(this.displayName)
				.shortDisplayName(this.shortDisplayName)
				.abbreviation(this.abbreviation)
				.color(this.color)
				.alternativeColor(this.alternateColor)
				.logo(this.getLogos()==null ? null : this.getLogos().get(0).getHref())
				.conference(TeamConferenceDivisionBind.valueOf(this.getAbbreviation()).getConference())
				.division(TeamConferenceDivisionBind.valueOf(this.getAbbreviation()).getDivision())
				.build();
	}
}
