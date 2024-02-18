package com.dteodoro.javari.connector.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dteodoro.javari.commons.dto.AuthenticationRequest;
import com.dteodoro.javari.commons.dto.AuthenticationResponse;
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
import com.dteodoro.javari.connector.http.AuthClient;
import com.dteodoro.javari.connector.http.GameClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoaderServiceImp implements LoaderService {

    private final TeamLoader teamLoader;
    private final ScheduleLoader scheduleLoader;
    private final SeasonLoader seasonLoader;
    private final StandingLoader standingLoader;
    private final GameClient gameClient;
    private final AuthClient authClient;

    @Value("${JAVARI_CONNECTOR_USER:root}")
    private String systemUserName;
    @Value("${JAVARI_CONNECTOR_PASSWORD:javari}")
    private String systemPassword;

    @Override
    public void loadTeams() {
        List<TeamImportDTO> teamsDto = (List<TeamImportDTO>) teamLoader.load();
        String accessToken = getAcessToken();

        for (var teamDTO : teamsDto.stream().map(TeamImportDTO::toDomainDto).toList()) {
            gameClient.saveTeam(accessToken, teamDTO);
        }
        // TODO implements fallback methods
    }

    @Override
    public void loadSchedules() {
        List<ScheduleImportDTO> importSchedules = (List<ScheduleImportDTO>) scheduleLoader.load();
        String accessToken = getAcessToken();
        for (var scheduleDTO : importSchedules.stream().map(ScheduleImportDTO::toDomainDto).toList()) {
            log.info("saving schedule on game service");
            gameClient.saveSchedule(accessToken, scheduleDTO);
        }

        // TODO implements fallback methods
    }

    @Override
    public void loadSeasons() {
        List<SeasonImportDTO> importSeasons = (List<SeasonImportDTO>) seasonLoader.load();
        String accessToken = getAcessToken();
        for (SeasonDTO seasonDTO : importSeasons.stream().map(SeasonImportDTO::toDomainDto).toList()) {
            gameClient.saveSeason(accessToken, seasonDTO);
        }
        // TODO implements fallback methods
    }

    @Override
    public void loadStandings() {
        List<StandingsTeamStatsImportDTO> importStandings = (List<StandingsTeamStatsImportDTO>) standingLoader.load();
        String accessToken = getAcessToken();
        for (TeamScoreDTO teamScoreDTO : importStandings.stream().map(StandingsTeamStatsImportDTO::toDomainDto)
                .toList()) {
            gameClient.saveTeamScore(accessToken, teamScoreDTO);
        }
        // TODO implements fallback methods
    }

    private String getAcessToken() {
        String accessToken = null;
        try {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            accessToken = auth != null ? (String) auth.getCredentials() : null;
            log.info("acessToken:" + accessToken);
            if (!StringUtils.hasText(accessToken)) {
                log.info("Connect to AuthServer...");
                ResponseEntity<AuthenticationResponse> authResponse = authClient
                        .authenticate(new AuthenticationRequest(systemUserName, systemPassword));
                log.info("responseAuth:" + authResponse.getBody());

                if (authResponse.getStatusCode().equals(HttpStatus.OK)) {
                    accessToken = authResponse.getBody().getAccessToken();
                    log.info("accessTokenResponse" + accessToken);
                }
            }
        } catch (Exception e) {
            log.error("cannot connect to AuthService:" + e.getMessage());
            e.printStackTrace();
        }
        return "Bearer " + accessToken;
    }
}
