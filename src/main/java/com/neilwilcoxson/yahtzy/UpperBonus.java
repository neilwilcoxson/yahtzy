package com.neilwilcoxson.yahtzy;

public class UpperBonus extends ScorecardLine {
	
	public static final int CATEGORY_VALUE = 35;
	public static final int THRESHOLD = 63;

	@Override
	public int score(int category, Dice[] dice) {
		//this category does not depend on dice values
		return 0;
	}
	
	public int score(ScorecardLine upperSubtotal) {
		return upperSubtotal.getValue() >= THRESHOLD ? CATEGORY_VALUE : 0;
	}
	
	public void commit(ScorecardLine upperSubtotal) {
		value = score(upperSubtotal);
		used = true;
	}

}
