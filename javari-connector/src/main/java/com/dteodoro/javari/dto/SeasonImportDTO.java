package com.dteodoro.javari.dto;

import java.io.Serializable;

import com.dteodoro.javari.enumeration.SeasonType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeasonImportDTO implements Serializable,ImportDataDTO {
	
	private static final long serialVersionUID = 1L;
	Integer year;
	Integer type;
	String slug;
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	@Override
	public String toString() {
		return "SeasonDTO [year=" + year + ", type=" + type + ", slug=" + slug + "]";
	}

	@Override
	public SeasonDTO toDomainDto() {
		return SeasonDTO.builder()
				.slug(this.getSlug())
				.type(SeasonType.of(this.getType()))
				.competitionYear(this.getYear())
				.build();
	}
}
