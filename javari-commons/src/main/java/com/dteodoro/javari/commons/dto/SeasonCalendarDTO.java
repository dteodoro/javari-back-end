package com.dteodoro.javari.commons.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

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
	Integer value;
	@NotNull
	LocalDateTime startDate;
	@NotNull
	LocalDateTime endDate;
}
