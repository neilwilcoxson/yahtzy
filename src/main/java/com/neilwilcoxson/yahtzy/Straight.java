package com.neilwilcoxson.yahtzy;

public class Straight extends ScorecardLine {
	public static final int SMALL_STRAIGHT_VALUE = 30;
	public static final int LARGE_STRAIGHT_VALUE = 40;

	@Override
	public int score(int category, Dice[] dice) {
		if(used) {
			return 0;
		}
		
		int categoryValue = (category == Scorecard.SMALL_STRAIGHT) ? SMALL_STRAIGHT_VALUE : LARGE_STRAIGHT_VALUE;
		
		int[] values = Scorecard.values(dice);
		boolean badOrder = false;
		
		for(int i = 0; i < values.length - 1; i++) {
			if(values[i] != values[i+1] - 1) {
				if(category == Scorecard.SMALL_STRAIGHT) {
					if(!badOrder && values[i] == values[i+1]) {
						badOrder = true;
						continue;
					}
				}
				
				return 0;
			}
		}
		
		return categoryValue;
	}

}
