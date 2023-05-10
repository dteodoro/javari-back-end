package com.dteodoro.javari.service;

import com.dteodoro.javari.domain.ScheduleLoader;
import com.dteodoro.javari.domain.TeamLoader;
import com.dteodoro.javari.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoaderServiceImp implements LoaderService{

    private final TeamLoader teamLoader;
    private final ScheduleLoader scheduleLoader;
    private final RestTemplate restTemplate;

    @Value("${api.core.url}")
    private String coreApiUrl;
    @Override
    public void loadTeams() {
        List<TeamImportDTO> teamsDto = (List<TeamImportDTO>) teamLoader.load();
        for (var teamDTO: teamsDto.stream().map(TeamImportDTO::toDomainDto).toList()) {
           restTemplate.postForObject(coreApiUrl + "/teams", teamDTO, TeamDTO.class);
        }
        //TODO implements fallback methods
    }
    @Override
    public void loadSchedules() {
        List<ScheduleImportDTO> importSchedules = (List<ScheduleImportDTO>) scheduleLoader.load();
        for (var scheduleDTO: importSchedules.stream().map(ScheduleImportDTO::toDomainDto).toList()) {
            restTemplate.postForObject(coreApiUrl + "/schedules",scheduleDTO,ScheduleDTO.class);
        }
    }

}
