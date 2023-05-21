package com.dteodoro.javari.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
	Integer week;

	@OneToOne
	Season season;

	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Schedule> schedules = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

}
