package com.dteodoro.javari.util;

import lombok.Getter;

public enum NFLProviderAPI {
	
	TEAMS_API("http://site.api.espn.com/apis/site/v2/sports/football/nfl/teams"),
	SCHEDULES_API("http://site.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard");

	@Getter 
	private String uri;

	NFLProviderAPI(String uri) {
		this.uri = uri;
	}
	
	
}
