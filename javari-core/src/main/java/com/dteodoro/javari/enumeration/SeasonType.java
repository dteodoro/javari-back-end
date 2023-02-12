package com.dteodoro.javari.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SeasonType {
	PRESEASON(1), REGULAR_SESSION(2), WILD_CARD(3);

	@Getter
	private int id;

	public static SeasonType of(int id) {
		for (SeasonType type : SeasonType.values()) {
			if (type.getId() == id)
				return type;
		}
		return null;
	}

}
