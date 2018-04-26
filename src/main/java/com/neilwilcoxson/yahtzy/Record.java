package com.neilwilcoxson.yahtzy;

import java.util.Comparator;

public class Record implements Comparator<Record> {
	protected String name = null;
	protected int score = 0;
	
	public Record() {
		
	}
	
	public Record(Player player) {
		this.name = player.getName();
		this.score = player.getScorecard().getScore(Scorecard.GRAND_TOTAL);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public int compare(Record a, Record b) {
		return a.score > b.score ? -1 : a.score == b.score ? 0 : 1;
	}
}
