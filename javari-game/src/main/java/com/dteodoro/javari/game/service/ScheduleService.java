package com.dteodoro.javari.game.service;

import com.dteodoro.javari.commons.dto.BetDTO;
import com.dteodoro.javari.commons.dto.CompetitorDTO;
import com.dteodoro.javari.commons.dto.ScheduleDTO;
import com.dteodoro.javari.commons.dto.ScheduleFilterDTO;

import com.dteodoro.javari.commons.enumeration.HomeAway;
import com.dteodoro.javari.commons.enumeration.ScheduleStatus;
import com.dteodoro.javari.core.domain.Bet;
import com.dteodoro.javari.core.domain.Competitor;
import com.dteodoro.javari.core.domain.Schedule;
import com.dteodoro.javari.core.repository.CompetitorRepository;
import com.dteodoro.javari.core.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepo;
    private final CompetitorRepository competitorRepo;
    private final TeamService teamService;
    @Lazy private BetService betService;
    private final ModelMapper mapper;

    public List<ScheduleDTO> findAll(UUID bettorId) {
        return scheduleRepo.findAll().stream().map(s -> convertToScheduleDTO(s, bettorId)).toList();
    }

    private ScheduleDTO convertToScheduleDTO(Schedule schedule, UUID bettorId) {
        Bet bet = schedule.getBets().stream().filter(s -> s.getBettorId().equals(bettorId)).findFirst().orElse(null);
        return ScheduleDTO.builder()
                .id(schedule.getId())
                .name(schedule.getName())
                .shortName(schedule.getShortName())
                .startDate(schedule.getStartDate())
                .status(schedule.getStatus())
                .competitors(List.of(mapper.map(schedule.getHomeCompetitor(), CompetitorDTO.class),
                        mapper.map(schedule.getAwayCompetitor(), CompetitorDTO.class)))
                .bet(bet != null ? mapper.map(bet, BetDTO.class) : null)
                .build();
    }

    private ScheduleDTO convertToScheduleDTO(Schedule schedule){
        return ScheduleDTO.builder()
                .id(schedule.getId())
                .name(schedule.getName())
                .shortName(schedule.getShortName())
                .startDate(schedule.getStartDate())
                .status(schedule.getStatus())
                .competitors(List.of(mapper.map(schedule.getHomeCompetitor(), CompetitorDTO.class),
                        mapper.map(schedule.getAwayCompetitor(), CompetitorDTO.class)))
                .build();
    }

    public boolean scheduleIsOpen(UUID scheduleId) {
        Optional<Schedule> schedule = scheduleRepo.findById(scheduleId);
        return schedule.map(s -> s.getStartDate().isAfter(LocalDateTime.now().plusMinutes(5))).orElse(false);
    }

    public Schedule findByCompetitionId(Long competitionId) {
        return scheduleRepo.findByCompetitionId(competitionId);
    }

    public void save(Schedule schedule) {
        competitorRepo.save(schedule.getHomeCompetitor());
        competitorRepo.save(schedule.getAwayCompetitor());
        scheduleRepo.save(schedule);
    }

    public void update(Schedule currentSchedule) {
        if (currentSchedule.getStatus().equals(ScheduleStatus.STATUS_FINAL)) {
            betService.setWin(currentSchedule);
        }
        scheduleRepo.save(currentSchedule);

    }

    public List<ScheduleDTO> findBySeason(Integer year, String slug, UUID bettorId) {
        List<ScheduleDTO> schedules = scheduleRepo.findBySeasonSlugAndSeasonCompetitionYear( slug, year).stream().map(s -> convertToScheduleDTO(s, bettorId)).toList();
        Map<UUID, BetDTO> betMap = betService.getLastBets(bettorId).stream().collect(Collectors.toMap(BetDTO::getScheduleId, Function.identity()));
        schedules.forEach(s -> s.setBet(betMap.get(s.getId())));
        return schedules;
    }

    public ScheduleFilterDTO getScheduleFilters() {
        return new ScheduleFilterDTO(scheduleRepo.findFilterMenuYear(), scheduleRepo.findFilterMenuSeason());
    }

    public List<ScheduleDTO> findByTeam(UUID teamId, String year, String slug) {
        return scheduleRepo.findByHomeCompetitorTeamIdOrAwayCompetitorTeamId(teamId, teamId).stream().map(this::convertToScheduleDTO).toList();
    }

    public void saveOrUpdate(ScheduleDTO scheduleDto) {
        Schedule currentSchedule = findByCompetitionId(scheduleDto.getCompetitionId());
        if (currentSchedule != null && !currentSchedule.getStatus().equals(scheduleDto.getStatus())) {
            updateSchedule(currentSchedule, scheduleDto);
        } else if (currentSchedule == null) {
            save(convertToSchedule(scheduleDto));
        }
    }

    private Schedule convertToSchedule(ScheduleDTO scheduleDto) {
        Competitor homeCompetitor = mapper.map(getCompetitor(scheduleDto, HomeAway.HOME), Competitor.class);
        Competitor awayCompetitor = mapper.map(getCompetitor(scheduleDto, HomeAway.AWAY), Competitor.class);
        homeCompetitor.setTeam(teamService.findByEspnId(homeCompetitor.getTeam().getEspnId()));
        awayCompetitor.setTeam(teamService.findByEspnId(awayCompetitor.getTeam().getEspnId()));
        return Schedule.builder()
                .name(scheduleDto.getName())
                .shortName(scheduleDto.getShortName())
                .startDate(scheduleDto.getStartDate())
                .status(scheduleDto.getStatus())
                .homeCompetitor(homeCompetitor)
                .awayCompetitor(awayCompetitor)
                .build();
    }

    private void updateSchedule(Schedule currentSchedule, ScheduleDTO scheduleDto) {
        currentSchedule.setStatus(scheduleDto.getStatus());
        CompetitorDTO homeCompetitor = getCompetitor(scheduleDto, HomeAway.HOME);
        currentSchedule.getHomeCompetitor().setWinner(homeCompetitor.isWinner());
        currentSchedule.getHomeCompetitor().setScore(homeCompetitor.getScore());
        CompetitorDTO awayCompetitor = getCompetitor(scheduleDto, HomeAway.AWAY);
        currentSchedule.getAwayCompetitor().setWinner(awayCompetitor.isWinner());
        currentSchedule.getAwayCompetitor().setScore(awayCompetitor.getScore());
        update(currentSchedule);
    }

    private CompetitorDTO getCompetitor(ScheduleDTO schedule, HomeAway homeAway) {
        return schedule.getCompetitors().stream().filter(c -> c.getHomeAway().equals(homeAway)).findFirst().orElse(null);
    }
}
