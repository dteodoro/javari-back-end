package com.dteodoro.javari.connector.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.dteodoro.javari.commons.dto.SeasonDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.util.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeasonImportDTO implements ImportDataDTO {

	String label;
	Integer value;
	LocalDateTime startDate;
	LocalDateTime endDate;
	List<SeasonCalendarImportDTO> entries;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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

	public List<SeasonCalendarImportDTO> getEntries() {
		return entries;
	}

	public void setEntries(List<SeasonCalendarImportDTO> entries) {
		this.entries = entries;
	}

	@Override
	public SeasonDTO toDomainDto() {
		return SeasonDTO.builder()
				.label(this.label)
				.slug(formatToSlug(this.label))
				.competitionYear(this.startDate.getYear())
				.seasonCalendars(entries.stream().map(SeasonCalendarImportDTO::toDomainDto).toList())
				.build();
	}

	private String formatToSlug(String label) {
		return StringUtils.hasText(label) ? label.trim().toLowerCase().replace(" ","-").replace("-","") : null;
	}
}
