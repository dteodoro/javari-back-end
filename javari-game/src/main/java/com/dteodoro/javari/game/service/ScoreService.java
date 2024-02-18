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

    private final ScoreRepository scoreRepo;

    public void setPoint(Bet bet, final Integer numberOfCloseSchedules) {
        Score currentScore = scoreRepo.findByBettorId(bet.getBettorId()).orElse(null);
        if (currentScore != null) {
            currentScore.setPoints(currentScore.getPoints() + bet.getBet().getScore());
            currentScore.setNumberOfHits(currentScore.getNumberOfHits() + 1);
            currentScore.setAmountBetMade(numberOfCloseSchedules);
            currentScore.updateEfficiencyPercentage();
            scoreRepo.save(currentScore);
        }
    }

    public Score save(Score score) {
        return scoreRepo.save(score);
    }

    public Score createScore(Bettor bettor) {
        Score currentScore = scoreRepo.findByBettorId(bettor.getId()).orElse(null);
        if (currentScore == null) {
            return scoreRepo.save(new Score(bettor));
        }
        return currentScore;
    }

    public void addToAmountBet(final Bet bet) {
        Score currentScore = scoreRepo.findByBettorId(bet.getBettorId()).orElse(null);
        if (currentScore != null) {
            currentScore.setAmountBetMade(currentScore.getAmountBetMade() + 1);
            scoreRepo.save(currentScore);
        }
    }

}
