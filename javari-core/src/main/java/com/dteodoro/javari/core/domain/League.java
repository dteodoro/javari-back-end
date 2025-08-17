package com.dteodoro.javari.core.domain;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID leagueId;
    @ManyToOne
    private Competition competition;
    private Set<Bettor> bettors;
    private LeagueStatus status;
}
