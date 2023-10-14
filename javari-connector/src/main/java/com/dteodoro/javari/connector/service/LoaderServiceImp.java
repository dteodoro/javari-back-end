package com.dteodoro.javari.connector.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dteodoro.javari.commons.dto.SeasonDTO;
import com.dteodoro.javari.commons.dto.TeamScoreDTO;
import com.dteodoro.javari.connector.domain.ScheduleLoader;
import com.dteodoro.javari.connector.domain.SeasonLoader;
import com.dteodoro.javari.connector.domain.StandingLoader;
import com.dteodoro.javari.connector.domain.TeamLoader;
import com.dteodoro.javari.connector.dto.ScheduleImportDTO;
import com.dteodoro.javari.connector.dto.SeasonImportDTO;
import com.dteodoro.javari.connector.dto.StandingsTeamStatsImportDTO;
import com.dteodoro.javari.connector.dto.TeamImportDTO;
import com.dteodoro.javari.connector.http.GameClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoaderServiceImp implements LoaderService{

    private final TeamLoader teamLoader;
    private final ScheduleLoader scheduleLoader;
    private final SeasonLoader seasonLoader;
    private final StandingLoader standingLoader;
    private final GameClient gameClient;

    @Override
    public void loadTeams() {
        List<TeamImportDTO> teamsDto = (List<TeamImportDTO>) teamLoader.load();
        for (var teamDTO: teamsDto.stream().map(TeamImportDTO::toDomainDto).toList()) {
            String accessToken = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
            gameClient.saveTeam(accessToken, teamDTO);
        }
        //TODO implements fallback methods
    }
    @Override
    public void loadSchedules() {
        List<ScheduleImportDTO> importSchedules = (List<ScheduleImportDTO>) scheduleLoader.load();
        String accessToken = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        for (var scheduleDTO: importSchedules.stream().map(ScheduleImportDTO::toDomainDto).toList()) {
            gameClient.saveSchedule(accessToken,scheduleDTO);
        }
        //TODO implements fallback methods
    }

    @Override
    public void loadSeasons() {
        Collection<SeasonImportDTO> importSeasons = (Collection<SeasonImportDTO>) seasonLoader.load();
        String accessToken = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        for (SeasonDTO seasonDTO : importSeasons.stream().map(SeasonImportDTO::toDomainDto).toList()) {
            gameClient.saveSeason(accessToken,seasonDTO);
        }
        //TODO implements fallback methods
    }

    @Override
    public void loadStandings() {
        List<StandingsTeamStatsImportDTO> importStandings =(List<StandingsTeamStatsImportDTO>) standingLoader.load();
        String accessToken = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        for(TeamScoreDTO teamScoreDTO : importStandings.stream().map(StandingsTeamStatsImportDTO::toDomainDto).toList()){
            gameClient.saveTeamScore(accessToken,teamScoreDTO);
        }
        //TODO implements fallback methods
    }
}
