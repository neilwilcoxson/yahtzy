package com.neilwilcoxson.yahtzy;

public class Game {
	public static final int NUM_DICE = 5;
	
	protected static Player[] players = null;
	protected static Dice[] dice = null;
	
	//IDEA: have dice in separate window from scorecard
	//IDEA: have possible scores pop up when mouse hovers over category
	
	public static void main(String[] args) {
		initGame();
	}
	
	public static void initGame() {
		GUI.draw();
		
		players = new Player[1];
		players[0] = new Player("Bob");
		
		dice = new Dice[NUM_DICE];
		
		for(int i = 0; i < NUM_DICE; i++) {
			dice[i] = new Dice();
		}
	}
	
	public static void recordScore(int category) {
		players[0].getScorecard().commit(category, dice);
	}
	
	public static int getScore(int category) {
		return players[0].getScorecard().getScore(category);
	}
	
	public static void setKeep(int flipKeep) {
		dice[flipKeep].setKeep();
	}
	
	public static void roll() {
		for(int i = 0; i < NUM_DICE; i++) {
			dice[i].roll();
		}
	}
	
	public static int[] getDiceValues() {
		int[] result = new int[NUM_DICE];
		
		for(int i = 0; i < NUM_DICE; i++) {
			result[i] = dice[i].getValue();
		}
		
		return result;
	}

}
