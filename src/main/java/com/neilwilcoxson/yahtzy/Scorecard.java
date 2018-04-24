package com.neilwilcoxson.yahtzy;

import java.util.Arrays;

public class Scorecard {
	protected static final int NUM_CATEGORIES = 19;
	protected ScorecardLine[] scores = null;
	protected int turnsRemaining = 13;
	
	public static final int ACES = 0;
	public static final int TWOS = 1;
	public static final int THREES = 2;
	public static final int FOURS = 3;
	public static final int FIVES = 4;
	public static final int SIXES = 5;
	public static final int UPPER_SUBTOTAL = 6;
	public static final int UPPER_BONUS = 7;
	public static final int UPPER_TOTAL = 8;
	public static final int THREE_OF_A_KIND = 9;
	public static final int FOUR_OF_A_KIND = 10;
	public static final int FULL_HOUSE = 11;
	public static final int SMALL_STRAIGHT = 12;
	public static final int LARGE_STRAIGHT = 13;
	public static final int YAHTZY = 14;
	public static final int YAHTZY_BONUS = 15;
	public static final int CHANCE = 16;
	public static final int LOWER_TOTAL = 17;
	public static final int GRAND_TOTAL = 18;
	
	public Scorecard() {
		scores = new ScorecardLine[NUM_CATEGORIES];
		
		for(int i = ACES; i <= SIXES; i++) {
			scores[i] = new Numeric();
		}
		
		scores[UPPER_SUBTOTAL] = new Total();
		scores[UPPER_BONUS] = new UpperBonus();
		scores[UPPER_TOTAL] = new Total();
		
		for(int i = THREE_OF_A_KIND; i <= FOUR_OF_A_KIND; i++) {
			scores[i] = new Kind();
		}
		
		scores[FULL_HOUSE] = new FullHouse();
		
		for(int i = SMALL_STRAIGHT; i <= LARGE_STRAIGHT; i++) {
			scores[i] = new Straight();
		}
		
		scores[YAHTZY] = new Yahtzy();
		scores[YAHTZY_BONUS] = new YahtzyBonus();
		
		scores[CHANCE] = new Chance();
		
		for(int i = LOWER_TOTAL; i <= GRAND_TOTAL; i++) {
			scores[i] = new Total();
		}
	}
	
	public int score(int category, Dice[] dice) {
		return scores[category].score(category, dice);
	}
	
	public boolean commit(int category, Dice[] dice) {
		boolean result = scores[category].commit(category, dice);
		
		updateTotals();
		turnsRemaining--;
		
		return result;
	}
	
	public void updateTotals() {
		((Total)scores[UPPER_SUBTOTAL]).commit(scores, ACES, SIXES);
		((UpperBonus)scores[UPPER_BONUS]).commit(scores[UPPER_SUBTOTAL]);
		((Total)scores[UPPER_TOTAL]).commit(scores, UPPER_SUBTOTAL, UPPER_BONUS);
		((Total)scores[LOWER_TOTAL]).commit(scores, THREE_OF_A_KIND, CHANCE);
		((Total)scores[GRAND_TOTAL]).commit(scores[UPPER_TOTAL], scores[LOWER_TOTAL]);
	}
	
	public int getScore(int category) {
		return scores[category].getValue();
	}
	
	public int getTurnsRemaining() {
		return turnsRemaining;
	}
	
	public static int sum(Dice[] dice) {
		int result = 0;
		
		for(int i = 0; i < dice.length; i++) {
			result += dice[i].getValue();
		}
		
		return result;
	}
	
	public static int[] values(Dice[] dice) {
		int[] values = new int[dice.length];
		
		for(int i = 0; i < dice.length; i++) {
			values[i] = dice[i].getValue();
		}
		
		Arrays.sort(values);
		
		return values;
	}
}
