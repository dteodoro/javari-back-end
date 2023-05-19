package com.dteodoro.javari.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Entity
public class SeasonCalendar {

	String label;
	String alternateLabel;
	String detail;
	LocalDateTime startDate;
	LocalDateTime endDate;
	Integer value;

	@ManyToOne
	Season season;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

}
