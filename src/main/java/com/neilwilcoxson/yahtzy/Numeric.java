package com.neilwilcoxson.yahtzy;

public class Numeric extends ScorecardLine {

	@Override
	public int score(int category, Dice[] dice) {
		if(used) {
			return 0;
		}
		
		int result = 0;
		
		for(int i = 0; i < dice.length; i++) {
			if(dice[i].getValue() == category + 1) {
				result += category + 1;
			}
		}
		
		return result;
	}

}
