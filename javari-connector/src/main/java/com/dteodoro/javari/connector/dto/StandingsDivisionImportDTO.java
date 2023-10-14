package com.dteodoro.javari.connector.dto;

import com.dteodoro.javari.commons.enumeration.NFLDivision;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class StandingsDivisionImportDTO implements Serializable {

    private NFLDivision abbreviation;
    private String name;
    private StandingsImportDTO standings;

    public NFLDivision getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(final NFLDivision abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public StandingsImportDTO getStandings() {
        return standings;
    }

    public void setStandings(final StandingsImportDTO standings) {
        this.standings = standings;
    }
}
