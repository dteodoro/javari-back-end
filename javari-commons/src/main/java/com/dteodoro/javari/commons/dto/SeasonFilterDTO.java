package com.dteodoro.javari.commons.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class SeasonFilterDTO {

    private UUID seasonId;
    private String seasonLabel;
    private List<WeekFilterDTO> weeks;

}
