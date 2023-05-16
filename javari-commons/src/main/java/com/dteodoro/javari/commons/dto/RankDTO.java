package com.dteodoro.javari.commons.dto;

import java.util.Set;

import lombok.Data;

@Data
public class RankDTO {

	private int year;
    private Set<BettorDTO> bettors; 
}
