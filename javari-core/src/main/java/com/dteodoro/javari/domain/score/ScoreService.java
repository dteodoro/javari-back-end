package com.dteodoro.javari.domain.score;

import com.dteodoro.javari.domain.bet.Bet;
import com.dteodoro.javari.domain.bet.BetEnum;
import com.dteodoro.javari.domain.bettor.Bettor;
import com.dteodoro.javari.domain.bettor.BettorService;
import com.dteodoro.javari.domain.bettor.Score;
import com.dteodoro.javari.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private static final Integer BET_POINT = 3;
    private static final Integer BET_TIE_POINT = 6;
    private final ScoreRepository scoreRepo;

    private final BettorService bettorService;
    public void setPoint(Bet bet) {
        Score currentScore = scoreRepo.findByBettorId(bet.getBettorId()).orElse(null);
        if(currentScore != null){
            currentScore.setPoints(currentScore.getPoints() + bet.getBet().getScore());
            currentScore.setNumberOfHits(currentScore.getNumberOfHits() + 1);
            scoreRepo.save(currentScore);
        }else{
            Bettor bettor = bettorService.findById(bet.getBettorId());
            Score score = new Score(bettor,bet);
            scoreRepo.save(score);
        }
    }

}
