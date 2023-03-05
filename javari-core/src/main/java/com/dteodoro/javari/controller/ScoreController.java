package com.dteodoro.javari.controller;

import com.dteodoro.javari.domain.bettor.Bettor;
import com.dteodoro.javari.domain.bettor.BettorService;
import com.dteodoro.javari.domain.score.Score;
import com.dteodoro.javari.domain.score.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@CrossOrigin(origins = "${javari.client.front}")
@RestController
@RequestMapping("/score")
@RequiredArgsConstructor
public class ScoreController {

    private final BettorService bettorService;
    private final ScoreService scoreService;
    @PostMapping("/{id}")
    public ResponseEntity<?> createScore(@PathVariable(name = "id",required = true) UUID bettorId){
        Bettor bettor = bettorService.findById(bettorId);
        if(bettor != null ){
            Score score = scoreService.save(new Score(bettor));
            bettor.setScore(score);
            bettorService.save(bettor);
            return ResponseEntity.created(URI.create("/score/"+bettorId)).build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
