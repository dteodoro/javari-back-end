package com.dteodoro.javari.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
	
	private UUID id;
	private String viewId;
	private String name;
	private String displayName;
	private String shortDisplayName;
	private String abbreviation;
	private String color;
	private String alternativeColor;
	private String logo;
	private String scoreScoreSummary;
}
