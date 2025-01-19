package com.dteodoro.javari.connector.util;

import lombok.Getter;

public enum NFLProviderAPI {
	
	TEAMS_API("https://site.api.espn.com/apis/site/v2/sports/football/nfl/teams"),
	SCHEDULES_API("https://site.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard"),
	STANDINGS_API("https://cdn.espn.com/core/nfl/standings?xhr=1");

	@Getter 
	private final String uri;

	NFLProviderAPI(String uri) {
		this.uri = uri;
	}
	
	
}
