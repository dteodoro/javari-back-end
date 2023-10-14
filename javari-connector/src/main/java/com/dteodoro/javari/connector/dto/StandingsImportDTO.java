package com.dteodoro.javari.connector.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class StandingsImportDTO {
    List<StandingsTeamStatsImportDTO> entries;

    public List<StandingsTeamStatsImportDTO> getEntries() {
        return entries;
    }

    public void setEntries(final List<StandingsTeamStatsImportDTO> entries) {
        this.entries = entries;
    }
}
