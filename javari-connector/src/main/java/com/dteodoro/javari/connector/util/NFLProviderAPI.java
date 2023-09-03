package com.dteodoro.javari.connector.util;

import lombok.Getter;

public enum NFLProviderAPI {
	
	TEAMS_API("https://site.api.espn.com/apis/site/v2/sports/football/nfl/teams"),
	SCHEDULES_API("https://site.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard");

	@Getter 
	private String uri;

	NFLProviderAPI(String uri) {
		this.uri = uri;
	}
	
	
}
