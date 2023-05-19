package com.dteodoro.javari.commons.dto;

import com.dteodoro.javari.commons.enumeration.SeasonType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeasonDTO implements DomainDTO{

    private UUID id;
    private String label;
    @NotBlank
    private String slug;
    @NotNull
    private Integer competitionYear;

    private List<SeasonCalendarDTO> seasonCalendars;

}
