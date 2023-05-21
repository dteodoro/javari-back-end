package com.dteodoro.javari.game.service;

import com.dteodoro.javari.commons.dto.*;

import com.dteodoro.javari.commons.enumeration.HomeAway;
import com.dteodoro.javari.commons.enumeration.ScheduleStatus;
import com.dteodoro.javari.core.domain.*;
import com.dteodoro.javari.core.repository.CompetitorRepository;
import com.dteodoro.javari.core.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepo;
    private final CompetitorRepository competitorRepo;
    private final TeamService teamService;
    private final SeasonService seasonService;
    @Lazy private BetService betService;
    private final ModelMapper mapper;

    public List<ScheduleDTO> findByBettorId(UUID bettorId) {
        return scheduleRepo.findAll().stream().map(s -> convertToScheduleDTO(s, bettorId)).toList();
    }

    private ScheduleDTO convertToScheduleDTO(Schedule schedule, UUID bettorId) {
        Bet bet = schedule.getBets().stream().filter(s -> s.getBettorId().equals(bettorId)).findFirst().orElse(null);
        ScheduleDTO scheduleDTO = convertToScheduleDTO(schedule);
        scheduleDTO.setBet(bet != null ? mapper.map(bet, BetDTO.class) : null);
        return scheduleDTO;
    }

    private ScheduleDTO convertToScheduleDTO(Schedule schedule){
        return ScheduleDTO.builder()
                .id(schedule.getId())
                .name(schedule.getName())
                .shortName(schedule.getShortName())
                .startDate(schedule.getStartDate())
                .status(schedule.getStatus())
                .seasonCalendar(convetToSeasonCalendarDTO(schedule.getSeasonCalendar()))
                .competitors(List.of(mapper.map(schedule.getHomeCompetitor(), CompetitorDTO.class),
                        mapper.map(schedule.getAwayCompetitor(), CompetitorDTO.class)))
                .build();
    }

    private SeasonCalendarDTO convetToSeasonCalendarDTO(SeasonCalendar seasonCalendar) {
        return SeasonCalendarDTO.builder()
                .season(mapper.map(seasonCalendar.getSeason(),SeasonDTO.class))
                .startDate(seasonCalendar.getStartDate())
                .endDate(seasonCalendar.getEndDate())
                .label(seasonCalendar.getLabel())
                .alternateLabel(seasonCalendar.getAlternateLabel())
                .week(seasonCalendar.getWeek())
                .detail(seasonCalendar.getDetail())
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
        Schedule scheduleSaved = scheduleRepo.save(schedule);
        SeasonCalendar seasonCalendar = scheduleSaved.getSeasonCalendar();
        seasonCalendar.getSchedules().add(schedule);
        seasonService.updateCalendar(seasonCalendar);
    }

    public void update(Schedule currentSchedule) {
        if (currentSchedule.getStatus().equals(ScheduleStatus.STATUS_FINAL)) {
            betService.setWin(currentSchedule);
        }
        scheduleRepo.save(currentSchedule);

    }

    public ScheduleFilterDTO getScheduleFilters() {
        return new ScheduleFilterDTO(scheduleRepo.findFilterMenuYear(), scheduleRepo.findFilterMenuSeason());
    }

    public List<ScheduleBySeasonDTO> findByTeam(UUID teamId, Integer year) {
        List<ScheduleBySeasonDTO> scheduleBySeason = new ArrayList<>();
        List<ScheduleDTO> scheulesDto = scheduleRepo.findByHomeCompetitorTeamIdOrAwayCompetitorTeamIdAndSeasonCalendarSeasonCompetitionYear(teamId,teamId, year)
                                                        .stream()
                                                        .filter(s -> s.getStatus().equals(ScheduleStatus.STATUS_FINAL))
                                                        .map(this::convertToScheduleDTO).toList();
        return scheulesDto.stream()
                .collect(Collectors.groupingBy(s -> s.getSeasonCalendar().getSeason().getLabel()))
                .entrySet().stream()
                .map(entry->new ScheduleBySeasonDTO(entry.getKey(),entry.getValue())).toList();

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
        SeasonCalendar seasonCalendar = seasonService.findByWeekAndSeasonSlugAndSeasonCompetitionYear(
                                                        scheduleDto.getWeek(),
                                                        scheduleDto.getSeason().getSlug(),
                                                        scheduleDto.getSeason().getCompetitionYear());
        return Schedule.builder()
                .competitionId(scheduleDto.getCompetitionId())
                .name(scheduleDto.getName())
                .shortName(scheduleDto.getShortName())
                .startDate(scheduleDto.getStartDate())
                .status(scheduleDto.getStatus())
                .homeCompetitor(homeCompetitor)
                .awayCompetitor(awayCompetitor)
                .seasonCalendar(seasonCalendar)
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
