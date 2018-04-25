package com.neilwilcoxson.yahtzy;

public class Game {
	public static final int NUM_DICE = 5;
	public static final int NUM_ROLLS = 3;
	
	//various states of the game
	public static final int MUST_ROLL = 0;
	public static final int MAY_SCORE = 1;
	public static final int MUST_SCORE = 2;
	public static final int GAME_OVER = 3;
	
	protected static Player[] players = null;
	protected static Dice[] dice = null;
	
	protected static int playerTurn;
	protected static int rollsRemaining;
	protected static int gameState;
	
	//IDEA: have dice in separate window from scorecard
	//IDEA: have possible scores pop up when mouse hovers over category
	
	public static void main(String[] args) {
		initGame();
	}
	
	public static void initGame() {
		GUI.draw();
		
		playerTurn = 0;
		rollsRemaining = NUM_ROLLS;
		gameState = MUST_ROLL;
		
		players = new Player[1];
		players[0] = new Player("Bob");
		
		dice = new Dice[NUM_DICE];
		
		for(int i = 0; i < NUM_DICE; i++) {
			dice[i] = new Dice();
		}
	}
	
	public static void recordScore(int category) {
		players[0].getScorecard().commit(category, dice);
		
		if(players[0].getTurnsRemaining() != 0) {
			rollsRemaining = NUM_ROLLS;
			gameState = MUST_ROLL;
		}else {
			gameState = GAME_OVER;
		}
	}
	
	public static int getScore(int category) {
		return players[0].getScorecard().getScore(category);
	}
	
	public static void setKeep(int flipKeep) {
		dice[flipKeep].toggleKeep();
	}
	
	public static void roll() {
		if(rollsRemaining == NUM_ROLLS) {
			for(int i = 0; i < NUM_DICE; i++) {
				dice[i].removeKeep();
			}
		}
		
		for(int i = 0; i < NUM_DICE; i++) {
			dice[i].roll();
		}
		
		rollsRemaining--;
		
		if(rollsRemaining <= NUM_ROLLS-1) {
			gameState = MAY_SCORE;
		}
		
		if(rollsRemaining == 0) {
			rollsRemaining = 3;
			gameState = MUST_SCORE;
		}
	}
	
	public static int[] getDiceValues() {
		int[] result = new int[NUM_DICE];
		
		for(int i = 0; i < NUM_DICE; i++) {
			result[i] = dice[i].getValue();
		}
		
		return result;
	}
	
	public static int[] getAvailableCategories() {
		int[] result = new int[players[0].getTurnsRemaining()];
		int len = 0;
		
		for(int i : Scorecard.SCORING_CATEGORIES) {
			if(!players[0].getScorecard().getUsed(i)) {
				result[len++] = i;
			}
		}
		
		return result;
	}
	
	public static int getState() {
		return gameState;
	}

}
