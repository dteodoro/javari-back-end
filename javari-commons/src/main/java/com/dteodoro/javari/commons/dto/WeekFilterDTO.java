package com.dteodoro.javari.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class WeekFilterDTO {

	UUID weekId;
	String weekLabel;


}
