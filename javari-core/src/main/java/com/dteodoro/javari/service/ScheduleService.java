package com.dteodoro.javari.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dteodoro.javari.domain.bet.Bet;
import com.dteodoro.javari.dto.ScheduleFilterDTO;
import com.dteodoro.javari.enumeration.ScheduleStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dteodoro.javari.domain.bet.BetService;
import com.dteodoro.javari.dto.BetDTO;
import com.dteodoro.javari.dto.CompetitorDTO;
import com.dteodoro.javari.dto.ScheduleDTO;
import com.dteodoro.javari.entity.Schedule;
import com.dteodoro.javari.enumeration.SeasonType;
import com.dteodoro.javari.repository.CompetitorRepository;
import com.dteodoro.javari.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepo;
    private final CompetitorRepository competitorRepo;
    @Lazy
    @Autowired
    private BetService betService;
    private final ModelMapper mapper;

    public Page<ScheduleDTO> findAll(UUID bettorId, Pageable pageable) {
        Page<Schedule> schedules = scheduleRepo.findAll(pageable);
        return schedules.map(s -> convertToScheduleDTO(s, bettorId));
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
                .bet(mapper.map(bet, BetDTO.class))
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

    public Page<ScheduleDTO> findBySeason(Integer year, String slug, UUID bettorId, Pageable pageable) {
        Page<ScheduleDTO> schedules = scheduleRepo.findBySeasonSlugAndSeasonCompetitionYear(pageable, slug, year).map(s -> convertToScheduleDTO(s, bettorId));
        Map<UUID, BetDTO> betMap = betService.getLastBets(bettorId).stream().collect(Collectors.toMap(BetDTO::getScheduleId, Function.identity()));
        schedules.forEach(s -> s.setBet(betMap.get(s.getId())));
        return schedules;
    }

    public ScheduleFilterDTO getScheduleFilters() {
        return new ScheduleFilterDTO(scheduleRepo.findFilterMenuYear(), scheduleRepo.findFilterMenuSeason());
    }
}
