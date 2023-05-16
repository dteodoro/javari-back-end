package com.dteodoro.javari.commons.dto;

import java.util.UUID;

import com.dteodoro.javari.commons.enumeration.NFLConference;
import com.dteodoro.javari.commons.enumeration.NFLDivision;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamDTO implements DomainDTO{
	private UUID id;
	@NotNull
	private Long espnId;
	private String viewId;
	@NotBlank
	private String name;
	@NotBlank
	private String displayName;
	@NotBlank
	private String shortDisplayName;
	@NotBlank
	private String abbreviation;
	@NotBlank
	private String color;
	@NotBlank
	private String alternativeColor;
	private String logo;
	private String scoreScoreSummary;
	@NotNull
	private NFLConference conference;
	@NotNull
	private NFLDivision division;
}
