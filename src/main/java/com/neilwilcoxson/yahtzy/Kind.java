package com.neilwilcoxson.yahtzy;

/**
 * 
 * @author Neil Wilcoxson
 *
 */
public class Kind extends ScorecardLine {

	@Override
	public int score(int category, Dice[] dice) {
		if(used) {
			return 0;
		}
		
		for(int i = 0; i < dice.length - 2; i++) {
			int numMatched = 1;
			for(int j = i + 1; j < dice.length; j++) {
				if(dice[i].getValue() == dice[j].getValue()) {
					numMatched++;
				}
				if(numMatched == (category - Scorecard.THREE_OF_A_KIND + 3)) {
					return Scorecard.sum(dice);
				}
			}
		}
		
		return 0;
	}

}
