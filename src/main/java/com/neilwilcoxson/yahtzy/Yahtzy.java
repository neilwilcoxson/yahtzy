package com.neilwilcoxson.yahtzy;

public class Yahtzy extends ScorecardLine {
	public static final int CATEGORY_VALUE = 50;
	
	@Override
	public int score(int category, Dice[] dice) {
		if(used) {
			return 0;
		}
		
		for(int i = 0; i < dice.length - 1; i++) {
			if(dice[i].getValue() != dice[i+1].getValue()) {
				return 0;
			}
		}
		
		return CATEGORY_VALUE;
	}

}
