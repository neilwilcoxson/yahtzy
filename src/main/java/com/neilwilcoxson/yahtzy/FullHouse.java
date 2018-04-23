package com.neilwilcoxson.yahtzy;

public class FullHouse extends ScorecardLine {
	
	public static final int CATEGORY_VALUE = 25;

	@Override
	public int score(int category, Dice[] dice) {
		if(used) {
			return 0;
		}
		
		int[] values = Scorecard.values(dice);
		
		if(values[dice.length/2] == values[0]) {
			if(values[dice.length-1] != values[dice.length/2 + 1]) {
				return 0;
			}
			if(values[dice.length/2] == values[dice.length/2 + 1]) {
				return 0;
			}
		}else if(values[dice.length/2] == values[dice.length-1]) {
			if(values[0] != values[dice.length/2 - 1]) {
				return 0;
			}
			if(values[dice.length/2] == values[dice.length/2 - 1]) {
				return 0;
			}
		}else {
			return 0;
		}
		
		return CATEGORY_VALUE;
	}

}
