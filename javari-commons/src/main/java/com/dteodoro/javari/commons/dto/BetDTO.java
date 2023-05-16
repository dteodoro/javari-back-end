package com.dteodoro.javari.commons.dto;

import java.util.UUID;


import com.dteodoro.javari.commons.enumeration.BetEnum;
import lombok.Data;

@Data
public class BetDTO implements DomainDTO {

	private UUID id;
	private UUID bettorId;
	private UUID scheduleId;
	private BetEnum bet;
	private Boolean win;
	private Integer score;
	
}
