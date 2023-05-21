package com.dteodoro.javari.game.service;

import com.dteodoro.javari.core.domain.Bet;
import com.dteodoro.javari.core.domain.Bettor;
import com.dteodoro.javari.core.domain.Score;
import com.dteodoro.javari.core.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private static final Integer BET_POINT = 3;
    private static final Integer BET_TIE_POINT = 6;
    private final ScoreRepository scoreRepo;

    public void setPoint(Bet bet) {
        Score currentScore = scoreRepo.findByBettorId(bet.getBettorId()).orElse(null);
        currentScore.setPoints(currentScore.getPoints() + bet.getBet().getScore());
        currentScore.setNumberOfHits(currentScore.getNumberOfHits() + 1);
        scoreRepo.save(currentScore);
    }

    public Score save(Score score) {
        return scoreRepo.save(score);
    }

    public Score createScore(Bettor bettor) {
        Score currentScore = scoreRepo.findByBettorId(bettor.getId()).orElse(null);
        if(currentScore==null) {
            return scoreRepo.save(new Score(bettor));
        }
        return currentScore;
    }
}