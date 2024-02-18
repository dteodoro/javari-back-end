package com.dteodoro.javari.commons.dto;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeasonDTO implements DomainDTO {

    private UUID id;
    private String label;
    @NotBlank
    private String slug;
    @NotNull
    private Integer competitionYear;

    private List<SeasonCalendarDTO> seasonCalendars;

}
