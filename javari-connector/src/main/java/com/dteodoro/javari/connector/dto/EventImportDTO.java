package com.dteodoro.javari.connector.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventImportDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String shortName;
	private EventSeasonImportDTO season;
	private WeekImportDTO week;
	private StatusImportDTO status;
	private List<CompetitionImportDTO> competitions;

	@Override
	public String toString() {
		return "EventDTO [name=" + name + ", shortName=" + shortName + ", season=" + season + ", competitions="
				+ competitions + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public StatusImportDTO getStatus() {
		return status;
	}

	public void setStatus(StatusImportDTO status) {
		this.status = status;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public EventSeasonImportDTO getSeason() {
		return season;
	}

	public void setSeason(EventSeasonImportDTO season) {
		this.season = season;
	}

	public List<CompetitionImportDTO> getCompetitions() {
		return competitions;
	}

	public void setCompetitions(List<CompetitionImportDTO> competitions) {
		this.competitions = competitions;
	}
	
}
