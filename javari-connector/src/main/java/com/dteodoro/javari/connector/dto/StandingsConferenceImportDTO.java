package com.dteodoro.javari.connector.dto;

import com.dteodoro.javari.commons.enumeration.NFLConference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class StandingsConferenceImportDTO {
    private NFLConference abbreviation;
    private List<StandingsDivisionImportDTO> groups;

    public NFLConference getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(final NFLConference abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<StandingsDivisionImportDTO> getGroups() {
        return groups;
    }

    public void setGroups(final List<StandingsDivisionImportDTO> groups) {
        this.groups = groups;
    }

}
