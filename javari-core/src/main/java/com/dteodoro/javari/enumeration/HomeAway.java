package com.dteodoro.javari.enumeration;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum HomeAway {
	@JsonProperty("home")
	HOME,
	@JsonProperty("away")
	AWAY
}
