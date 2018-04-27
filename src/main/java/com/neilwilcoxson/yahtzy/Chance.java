package com.neilwilcoxson.yahtzy;

/**
 * 
 * @author Neil Wilcoxson
 *
 */
public class Chance extends ScorecardLine {

	@Override
	public int score(int category, Dice[] dice) {
		if(used) {
			return 0;
		}
		
		return Scorecard.sum(dice);
	}

}
