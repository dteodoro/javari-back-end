package com.dteodoro.javari.game.service;

import com.dteodoro.javari.commons.dto.BetDTO;
import com.dteodoro.javari.commons.enumeration.BetEnum;
import com.dteodoro.javari.commons.enumeration.ScheduleStatus;
import com.dteodoro.javari.core.domain.Bet;
import com.dteodoro.javari.core.domain.Schedule;
import com.dteodoro.javari.core.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BetService {

	private final BetRepository betRepo;
	private final ScheduleService scheduleService;
	private final ScoreService scoreService;
	private final ModelMapper modelMapper;

	public ResponseEntity<BetDTO> makeBet(BetDTO betDto) {
		Bet bet = modelMapper.map(betDto, Bet.class);
		Optional<Bet> currentBet = betRepo.findByScheduleIdAndBettorId(betDto.getScheduleId(),betDto.getBettorId());
		if (scheduleIsOpen(betDto.getScheduleId())) {
			currentBet.ifPresent(value -> bet.setId(value.getId()));
			if(bet.getBet()==null){
				betRepo.delete(bet);
			}else {
				betRepo.save(bet);
			}
			return ResponseEntity.created(URI.create("/bet/" + bet.getId())).body(modelMapper.map(bet, BetDTO.class));
		}
		throw new IllegalStateException("cannot make bet for schedules in process or closed");

	}
	
	public List<BetDTO> getLastBets(UUID bettorId) {
		List<Bet> bets = betRepo.findByBettorId(bettorId).orElse(Collections.emptyList());
		return bets.stream().map(this::convertToBetDTO).toList();
	}

	public void setWin(Schedule schedule){
		if(schedule.getStatus().equals(ScheduleStatus.STATUS_FINAL)){
			Integer schedulesClosed = scheduleService.countCloseSchedules();
			BetEnum winner = getWinner(schedule);
			schedule.getBets().forEach(bet -> {
				bet.setWin(bet.getBet().equals(winner));
				update(bet,schedulesClosed);
			});
		}else{
			log.error("Schedule hasn't finished yet ");
		}
	}

	private void update(Bet bet,Integer numberOfSchedulesClose) {
		if(bet.getWin()){
			scoreService.setPoint(bet,numberOfSchedulesClose);
		}
		betRepo.save(bet);
	}

	private BetEnum getWinner(Schedule schedule) {
		if (schedule.getHomeCompetitor().getWinner()) {
			return BetEnum.HOME;
		}
		if (schedule.getAwayCompetitor().getWinner()) {
			return BetEnum.AWAY;
		}
		return BetEnum.TIE;
	}

	private BetDTO convertToBetDTO(Bet bet) {
		return modelMapper.map(bet, BetDTO.class);
	}
	
	private boolean scheduleIsOpen(UUID scheduleId) {
		return scheduleService.scheduleIsOpen(scheduleId);
	}
}
