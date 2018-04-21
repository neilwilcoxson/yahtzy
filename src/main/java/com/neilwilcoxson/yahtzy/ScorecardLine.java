package com.neilwilcoxson.yahtzy;

public abstract class ScorecardLine {
	protected int value;
	protected boolean used;
	
	public ScorecardLine() {
		value = 0;
		used = false;
	}
	
	public abstract int score(int category, Dice[] dice);
	
	public boolean commit(int category, Dice[] dice) {
		if(used) {
			return false;
		}
		
		value = score(category, dice);
		used = true;
		
		return true;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public boolean used() {
		return used;
	}
}
