package com.dteodoro.javari.game.service;

import com.dteodoro.javari.commons.dto.CompetitorDTO;
import com.dteodoro.javari.commons.dto.ScheduleBySeasonDTO;
import com.dteodoro.javari.core.domain.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


@Slf4j
@Service
public class StrengthOfVictoryService {

    private final ObjectProvider<ScheduleService> scheduleServiceProvider;

    public StrengthOfVictoryService(ObjectProvider<ScheduleService> scheduleServiceProvider) {
        this.scheduleServiceProvider = scheduleServiceProvider;
    }

    @Async
    public double calcStrengthOfVictory(Team team) {
        log.info("Calculating Strength of Victory for teamId: " + team.getId());
        ScheduleService scheduleService = scheduleServiceProvider.getIfAvailable();
        assert scheduleService != null;
        List<ScheduleBySeasonDTO> scheduleByTeam = scheduleService.findByTeam(team.getId(), LocalDate.now(ZoneId.of("America/Sao_Paulo")).getYear());
        List<TeamStrengthOfVictory> strengths = scheduleByTeam.stream()
                .flatMap(ss -> ss.getSchedules().stream())
                .flatMap(s -> s.getCompetitors().stream())
                .filter(c -> !c.getId().equals(team.getId()))
                .filter(c -> !c.isWinner())
                .map(CompetitorDTO::getTeam)
                .distinct()
                .map(t -> new TeamStrengthOfVictory(t.getScoreWins(), (t.getGamesPlayed() - t.getScoreTies())))
                .toList();

        int totalWins = strengths.stream().mapToInt(TeamStrengthOfVictory::wins).sum();
        int totalGames = strengths.stream().mapToInt(TeamStrengthOfVictory::gamesPlayed).sum();
        return totalGames == 0 ? 0.0 : (double) totalWins / totalGames;
    }
}
record TeamStrengthOfVictory(int wins,int gamesPlayed){}