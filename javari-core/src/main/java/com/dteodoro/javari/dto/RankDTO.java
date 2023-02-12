package com.dteodoro.javari.dto;

import java.util.Set;

import lombok.Data;

@Data
public class RankDTO {

	private int year;
    private Set<BettorDTO> bettors; 
}
