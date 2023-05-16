package com.dteodoro.javari.connector.service;

import com.dteodoro.javari.connector.domain.ScheduleLoader;
import com.dteodoro.javari.connector.domain.TeamLoader;
import com.dteodoro.javari.connector.dto.*;
import com.dteodoro.javari.connector.http.GameClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoaderServiceImp implements LoaderService{

    private final TeamLoader teamLoader;
    private final ScheduleLoader scheduleLoader;
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
        for (var scheduleDTO: importSchedules.stream().map(ScheduleImportDTO::toDomainDto).toList()) {
            String accessToken = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
            gameClient.saveSchedule(accessToken,scheduleDTO);
        }
        //TODO implements fallback methods
    }

}
