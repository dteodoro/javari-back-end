package com.dteodoro.javari.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamScoreDTO implements DomainDTO {

    private Long teamId;
    private Integer wins;
    private Integer losses;
    private Integer ties;
    private String winPercent;
    private String home;
    private String road;
    private String versusDiv;
    private String versusConf;
    private Integer pointsFor;
    private Integer pointsAgainst;
    private String pointDifferential;
    private String streak;
    private String seasonName;
    private Integer seasonYear;

}
