package com.dteodoro.javari.dto;

import java.util.UUID;

import com.dteodoro.javari.enumeration.HomeAway;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompetitorDTO {

	private UUID id;
	private byte position;
	private HomeAway homeAway;
	private boolean winner;
	private byte score;
	private TeamDTO team;
}
