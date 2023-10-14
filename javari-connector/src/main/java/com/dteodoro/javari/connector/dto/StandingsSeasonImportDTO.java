package com.dteodoro.javari.connector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StandingsSeasonImportDTO {

    @JsonProperty("season")
    String seasonYear;
    String seasonName;

    public String getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(final String seasonYear) {
        this.seasonYear = seasonYear;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(final String seasonName) {
        this.seasonName = seasonName;
    }
}
