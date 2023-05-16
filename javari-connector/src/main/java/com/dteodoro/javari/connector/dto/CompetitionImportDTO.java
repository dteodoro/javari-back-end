package com.dteodoro.javari.connector.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompetitionImportDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private LocalDateTime startDate;
	private boolean timeValid;
	private boolean conferenceCompetition;
	private VenueImportDTO venue;
	private List<CompetitorImportDTO> competitors;
	private CompetitionStatusImportDTO status;
	
	@JsonGetter
	public List<CompetitorImportDTO> getCompetitors() {
		return competitors;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public boolean isTimeValid() {
		return timeValid;
	}

	public void setTimeValid(boolean timeValid) {
		this.timeValid = timeValid;
	}

	public boolean isConferenceCompetition() {
		return conferenceCompetition;
	}

	public void setConferenceCompetition(boolean conferenceCompetition) {
		this.conferenceCompetition = conferenceCompetition;
	}

	public VenueImportDTO getVenue() {
		return venue;
	}

	public void setVenue(VenueImportDTO venue) {
		this.venue = venue;
	}

	public CompetitionStatusImportDTO getStatus() {
		return status;
	}

	public void setStatus(CompetitionStatusImportDTO status) {
		this.status = status;
	}

	public void setCompetitors(List<CompetitorImportDTO> competitors) {
		this.competitors = competitors;
	}

	@Override
	public String toString() {
		return "CompetitionDTO [id=" + id + ", startDate=" + startDate + ", timeValid=" + timeValid
				+ ", conferenceCompetition=" + conferenceCompetition + ", venue=" + venue + ", competitors="
				+ competitors + ", status=" + status + "]";
	}
	
    
}
