package com.neilwilcoxson.yahtzy;

public class Player {
	protected static int numPlayers = 0;
	protected String name = null;
	protected Scorecard scorecard = null;
	
	public Player() {
		name = "Player " + ++numPlayers;
		scorecard = new Scorecard();
	}
	
	public Player(String name) {
		this.name = name;
		scorecard = new Scorecard();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static int getNumPlayers() {
		return numPlayers;
	}

	public Scorecard getScorecard() {
		return scorecard;
	}
	
	public int getTurnsRemaining() {
		return scorecard.getTurnsRemaining();
	}
	
	public static void removePlayer() {
		if(numPlayers > 0) {
			numPlayers--;
		}
	}
}
