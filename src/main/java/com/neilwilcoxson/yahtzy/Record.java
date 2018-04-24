package com.neilwilcoxson.yahtzy;

public class Record {
	protected String name = null;
	protected int score = 0;
	
	public Record(Player player) {
		this.name = player.getName();
		this.score = player.getScorecard().getScore(Scorecard.GRAND_TOTAL);
	}
}
