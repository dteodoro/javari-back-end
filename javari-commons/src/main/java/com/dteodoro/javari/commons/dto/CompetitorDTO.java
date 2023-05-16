package com.dteodoro.javari.commons.dto;

import java.util.UUID;

import com.dteodoro.javari.commons.enumeration.HomeAway;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompetitorDTO implements DomainDTO{

	private UUID id;
	@NotNull
	private byte position;
	@NotNull
	private HomeAway homeAway;
	@NotNull
	private boolean winner;
	private byte score;
	@NotNull
	private TeamDTO team;
}
