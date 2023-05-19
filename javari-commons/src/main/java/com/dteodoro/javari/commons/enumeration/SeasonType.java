package com.dteodoro.javari.commons.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SeasonType {
	PRESEASON(1,"preseaon"), REGULAR_SEASON(2,"regular-season"), POSTSEASON(3,"postseason"),OFF_SEASON(4,"off-season");

	@Getter private int id;
	@Getter private String slug;

	public static SeasonType of(int id) {
		for (SeasonType type : SeasonType.values()) {
			if (type.getId() == id)
				return type;
		}
		return null;
	}

	public static SeasonType of(String slug){
		for (SeasonType type : SeasonType.values()) {
			if (type.getSlug().equals(slug))
				return type;
		}
		return null;
	}

}
