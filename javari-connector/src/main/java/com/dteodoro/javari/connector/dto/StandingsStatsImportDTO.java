package com.dteodoro.javari.connector.dto;

import com.dteodoro.javari.commons.enumeration.NFLStats;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class StandingsStatsImportDTO implements Serializable {
    private String type;
    private String displayValue;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(final String displayValue) {
        this.displayValue = displayValue;
    }
}
