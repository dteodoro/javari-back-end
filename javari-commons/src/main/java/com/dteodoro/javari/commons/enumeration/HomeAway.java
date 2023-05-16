package com.dteodoro.javari.commons.enumeration;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum HomeAway {
	@JsonProperty("home")
	HOME,
	@JsonProperty("away")
	AWAY
}
