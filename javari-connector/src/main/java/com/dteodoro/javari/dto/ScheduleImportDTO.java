package com.dteodoro.javari.dto;

import com.dteodoro.javari.enumeration.ScheduleStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class ScheduleImportDTO implements Serializable, ImportDataDTO {

    private Long competitionId;
    private String name;
    private String shortName;
    private LocalDateTime startDate;
    private SeasonImportDTO season;
    private String status;
    private CompetitorImportDTO homeCompetitor;
    private CompetitorImportDTO awayCompetitor;

    @Override
    public ScheduleDTO toDomainDto() {
        return ScheduleDTO.builder()
                .competitionId(this.getCompetitionId())
                .name(this.getName())
                .shortName(this.getShortName())
                .startDate(this.getStartDate())
                .status(ScheduleStatus.valueOf(this.getStatus()))
                .season(this.getSeason().toDomainDto())
                .competitors(
                        List.of(this.getHomeCompetitor().toDomainDto(),
                                this.getAwayCompetitor().toDomainDto()
                        )
                )
                .build();
    }
}
