package com.neilwilcoxson.yahtzy;

public class Total extends ScorecardLine {
	
	public Total() {
		super();
		used = true;
	}

	@Override
	public int score(int category, Dice[] dice) {
		//the values on the dice do not influence how totals are calculated
		return 0;
	}

	public int score(ScorecardLine[] scores, int start, int end) {	
		int total = 0;
		
		for(int i = start; i <= end; i++) {
			total += scores[i].getValue();
		}
		
		return total;
	}
	
	public int score(ScorecardLine line1, ScorecardLine line2) {
		return line1.getValue() + line2.getValue();
	}
	
	public void commit(ScorecardLine[] scores, int start, int end) {
		value = score(scores, start, end);
	}
	
	public void commit(ScorecardLine line1, ScorecardLine line2) {
		value = score(line1, line2);
	}
}
