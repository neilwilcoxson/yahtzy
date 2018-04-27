package com.neilwilcoxson.yahtzy;

import java.io.File;

/**
 * 
 * @author Neil Wilcoxson
 *
 */
public class Game {
	public static final int NUM_DICE = 5;
	public static final int NUM_ROLLS = 3;
	
	//various states of the game
	public static final int WAITING = 0;
	public static final int MUST_ROLL = 1;
	public static final int MAY_SCORE = 2;
	public static final int MUST_SCORE = 3;
	public static final int GAME_OVER = 4;
	
	protected static Player[] players = null;
	protected static Dice[] dice = null;
	protected static HighScores hs = null;
	
	protected static int playerTurn;
	protected static int rollsRemaining;
	protected static int[] gameState;
	protected static String[] gameConfig;
	
	//IDEA: have possible scores pop up when mouse hovers over category
	
	public static void main(String[] args) {
		gameConfig = GUI.config();
		initGame();
		GUI.draw();
	}
	
	public static void initGame() {
		playerTurn = 0;
		rollsRemaining = NUM_ROLLS;
		
		players = new Player[Integer.parseInt(gameConfig[0])];
		
		for(int i = 1; i < gameConfig.length; i++) {
			players[i-1] = new Player(gameConfig[i]);
		}
		
		dice = new Dice[NUM_DICE];
		
		for(int i = 0; i < NUM_DICE; i++) {
			dice[i] = new Dice();
		}
		
		gameState = new int[players.length];
		
		gameState[0] = MUST_ROLL;
		
		for(int i = 1; i < gameState.length; i++) {
			gameState[i] = WAITING;
		}
	}
	
	public static int calculateScore(int category) {
		return players[playerTurn].getScorecard().score(category, dice);
	}
	
	public static void recordScore(int category) {
		Scorecard s = players[playerTurn].getScorecard();
		
		if(s.getScore(Scorecard.YAHTZY) == 50) {
			s.commit(Scorecard.YAHTZY_BONUS, dice);
		}
		
		s.commit(category, dice);
		
		if(players[playerTurn].getTurnsRemaining() == 0) {
			gameState[playerTurn] = GAME_OVER;
		}
	}
	
	public static int getScore(int category) {
		return players[playerTurn].getScorecard().getScore(category);
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
			gameState[playerTurn] = MAY_SCORE;
		}
		
		if(rollsRemaining == 0) {
			rollsRemaining = 3;
			gameState[playerTurn] = MUST_SCORE;
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
		int[] result = new int[players[playerTurn].getTurnsRemaining()];
		int len = 0;
		
		for(int i : Scorecard.SCORING_CATEGORIES) {
			if(!players[playerTurn].getScorecard().getUsed(i)) {
				result[len++] = i;
			}
		}
		
		return result;
	}
	
	public static int getState() {
		return gameState[playerTurn];
	}
	
	public static int getState(int playerID) {
		return gameState[playerID];
	}
	
	public static int getNumPlayers() {
		return players.length;
	}
	
	public static String getPlayerName() {
		return players[playerTurn].getName();
	}
	
	public static String getPlayerName(int i) {
		return players[i].getName();
	}
	
	public static int getCurrentPlayerID() {
		return playerTurn;
	}
	
	public static int getRollsRemaining() {
		return rollsRemaining;
	}
	
	public static void nextPlayer() {
		rollsRemaining = NUM_ROLLS;
		if(gameState[playerTurn] != GAME_OVER) {
			gameState[playerTurn] = WAITING;
		}
		
		playerTurn++;
		playerTurn %= players.length;
		
		if(gameState[playerTurn] != GAME_OVER) {
			gameState[playerTurn] = MUST_ROLL;
		}
	}
	
	public static Object[][] getHighScores() {
		File f = new File(HighScores.FILENAME);
		
		if(f.exists() && !f.isDirectory()) {
			hs = HighScores.load();
		}else {
			hs = new HighScores();
		}
		
		hs.sort();
		
		return hs.getData();
	}
	
	public static void saveHighScores() {
		getHighScores();
		
		for(Player p : players) {
			hs.add(new Record(p));
		}
		
		HighScores.save(hs);
	}
}
