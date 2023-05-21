package com.dteodoro.javari.commons.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeasonCalendarDTO implements DomainDTO{
	@NotBlank
	String label;
	@NotBlank
	String alternateLabel;
	@NotBlank
	String detail;
	@NotNull
	Integer week;
	@NotNull
	LocalDateTime startDate;
	@NotNull
	LocalDateTime endDate;
	SeasonDTO season;
}
