package com.dteodoro.javari.domain.bet;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dteodoro.javari.domain.score.ScoreService;
import com.dteodoro.javari.entity.Competitor;
import com.dteodoro.javari.entity.Schedule;
import com.dteodoro.javari.enumeration.ScheduleStatus;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dteodoro.javari.dto.BetDTO;
import com.dteodoro.javari.repository.BetRepository;
import com.dteodoro.javari.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class BetService {

	private final BetRepository betRepo;
	@Lazy @Autowired 
	private ScheduleService scheduleService;

	private ScoreService scoreService;
	private final ModelMapper modelMapper;

	public ResponseEntity<BetDTO> makeBet(BetDTO betDto) {
		Bet bet = modelMapper.map(betDto, Bet.class);
		Optional<Bet> currentBet = betRepo.findByScheduleId(betDto.getScheduleId());
		if (sheduleIsOpen(betDto.getScheduleId())) {
			if(currentBet.isPresent()) {
				bet.setId(currentBet.get().getId());
			}
			betRepo.save(bet);
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
			BetEnum winner = getWinner(schedule);
			schedule.getBets().forEach(bet -> {
				bet.setWin(bet.getBet().equals(winner));
				update(bet);
			});
		}else{
			log.error("Schedule hasn't finished yet ");
		}
	}

	private void update(Bet bet) {
		if(bet.getWin()){
			scoreService.setPoint(bet);
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
	
	private boolean sheduleIsOpen(UUID scheduleId) {
		return scheduleService.scheduleIsOpen(scheduleId);
	}
}
