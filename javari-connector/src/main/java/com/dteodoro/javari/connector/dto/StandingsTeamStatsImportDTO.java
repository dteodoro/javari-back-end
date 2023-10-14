package com.dteodoro.javari.connector.dto;

import java.util.List;

import com.dteodoro.javari.commons.dto.TeamScoreDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.RequiredArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@RequiredArgsConstructor
public class StandingsTeamStatsImportDTO implements ImportDataDTO {

    List<StandingsStatsImportDTO> stats;
    StandingsTeamImportDTO team;

    Integer seasonYear;
    String seasonName;

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(final String seasonName) {
        this.seasonName = seasonName;
    }

    public Integer getSeasonYear() {
        return seasonYear;
    }

    public void setSeasonYear(final Integer seasonYear) {
        this.seasonYear = seasonYear;
    }

    public List<StandingsStatsImportDTO> getStats() {
        return stats;
    }

    public void setStats(final List<StandingsStatsImportDTO> stats) {
        this.stats = stats;
    }

    public StandingsTeamImportDTO getTeam() {
        return team;
    }

    public void setTeam(final StandingsTeamImportDTO team) {
        this.team = team;
    }

    @Override
    public TeamScoreDTO toDomainDto() {
        var teamScore = new TeamScoreDTO();
        teamScore.setTeamId(Long.valueOf(this.team.getId()));
        teamScore.setSeasonName(this.seasonName);
        teamScore.setSeasonYear(this.seasonYear);
        for (StandingsStatsImportDTO stats : this.getStats()){
            switch (stats.getType()){
                case "wins" -> teamScore.setWins(Integer.valueOf(stats.getDisplayValue()));
                case "losses" -> teamScore.setLosses(Integer.valueOf(stats.getDisplayValue()));
                case "ties" -> teamScore.setTies(Integer.valueOf(stats.getDisplayValue()));
                case "winpercent" -> teamScore.setWinPercent(stats.getDisplayValue());
                case "home" -> teamScore.setHome(stats.getDisplayValue());
                case "road" -> teamScore.setRoad(stats.getDisplayValue());
                case "vsdiv" -> teamScore.setVersusDiv(stats.getDisplayValue());
                case "vsconf" -> teamScore.setVersusConf(stats.getDisplayValue());
                case "pointsfor" -> teamScore.setPointsFor(Integer.valueOf(stats.getDisplayValue()));
                case "pointsagainst" -> teamScore.setPointsAgainst(Integer.valueOf(stats.getDisplayValue()));
                case "differential" -> teamScore.setPointDifferential(stats.getDisplayValue());
                case "streak" -> teamScore.setStreak(stats.getDisplayValue());
            }
        }
        return teamScore;
    }

}
