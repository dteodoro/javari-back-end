package com.dteodoro.javari.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleFilterDTO {
    private List<String> years;
    private List<String> seasons;

    public ScheduleFilterDTO(List<String> years, List<String> seasons) {
        this.years = years;
        this.seasons = seasons;
    }
}
