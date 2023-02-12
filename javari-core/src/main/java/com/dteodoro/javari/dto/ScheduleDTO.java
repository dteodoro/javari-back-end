package com.dteodoro.javari.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.dteodoro.javari.enumeration.ScheduleStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
	
	private UUID id;
	private String name;
	private String shortName;
	private LocalDateTime startDate;
	private ScheduleStatus status;
	private List<CompetitorDTO> competitors;
	private BetDTO bet;
}
