package com.dteodoro.javari.commons.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScheduleBySeasonDTO {
	String seasonName;
	String weekName;
	List<ScheduleDTO> schedules;

	public ScheduleBySeasonDTO(String seasonName, List<ScheduleDTO> schedules) {
		this.seasonName = seasonName;
		this.schedules = schedules;
	}
}
