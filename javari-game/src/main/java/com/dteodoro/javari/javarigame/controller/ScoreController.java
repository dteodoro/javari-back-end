package com.dteodoro.javari.javarigame.controller;


import com.dteodoro.javari.javaricore.domain.Bettor;
import com.dteodoro.javari.javaricore.domain.Score;
import com.dteodoro.javari.javarigame.service.BettorService;
import com.dteodoro.javari.javarigame.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/game/score")
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
