package com.dteodoro.javari.enumeration;

public enum BetEnum {

	HOME(3),AWAY(3),TIE(6),
	;
	private int score;

	BetEnum(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}
}
