package com.dteodoro.javari.dto;

import com.dteodoro.javari.enumeration.SeasonType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeasonDTO implements DomainDTO{
    private UUID id;
    @NotBlank
    private String slug;
    @NotNull
    private SeasonType type;
    @NotNull
    private Integer competitionYear;

}
