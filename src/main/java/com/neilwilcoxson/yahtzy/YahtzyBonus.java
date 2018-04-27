package com.neilwilcoxson.yahtzy;

/**
 * 
 * @author Neil Wilcoxson
 *
 */
public class YahtzyBonus extends ScorecardLine {
	
	public static final int CATEGORY_VALUE = 100;

	@Override
	public int score(int category, Dice[] dice) {
		
		for(int i = 0; i < dice.length - 1; i++) {
			if(dice[i].getValue() != dice[i+1].getValue()) {
				return value;
			}
		}
		
		return value + CATEGORY_VALUE;
	}
	
	@Override
	public boolean commit(int category, Dice[] dice) {
		value = score(category, dice);
		
		return true;
	}
	
}
