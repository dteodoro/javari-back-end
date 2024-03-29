package com.dteodoro.javari.commons.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.dteodoro.javari.commons.enumeration.ScheduleStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO implements DomainDTO{
	
	private UUID id;
	@NotNull
	private Long competitionId;
	@NotBlank
	private String name;
	@NotBlank
	private String shortName;
	@NotNull
	private LocalDateTime startDate;
	private SeasonCalendarDTO seasonCalendar;
	@NotNull
	private ScheduleStatus status;
	@NotEmpty
	private List<CompetitorDTO> competitors;
	private Integer week;
	private SeasonDTO season;
	private BetDTO bet;
}
