package com.dteodoro.javari.connector.dto;

import com.dteodoro.javari.commons.enumeration.NFLDivision;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class StandingsDivisionsImportDTO {
    private String name;
    private NFLDivision abbreviation;

    private StandingsImportDTO standings;

}
