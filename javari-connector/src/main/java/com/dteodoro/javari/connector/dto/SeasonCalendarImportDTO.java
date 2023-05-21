package com.dteodoro.javari.connector.dto;

import com.dteodoro.javari.commons.dto.SeasonCalendarDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeasonCalendarImportDTO implements ImportDataDTO{

	String label;
	String alternateLabel;
	String detail;
	Integer value;
	LocalDateTime startDate;
	LocalDateTime endDate;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAlternateLabel() {
		return alternateLabel;
	}

	public void setAlternateLabel(String alternateLabel) {
		this.alternateLabel = alternateLabel;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	@Override
	public SeasonCalendarDTO toDomainDto() {
		return SeasonCalendarDTO.builder()
				.label(this.label)
				.alternateLabel(this.alternateLabel)
				.detail(this.detail)
				.week(this.value)
				.startDate(this.startDate)
				.endDate(this.endDate)
				.build();
	}
}
