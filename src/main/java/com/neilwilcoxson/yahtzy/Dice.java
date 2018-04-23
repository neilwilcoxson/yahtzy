package com.neilwilcoxson.yahtzy;

public class Dice {
	protected int value;
	protected static final int MIN_VALUE = 1;
	protected static final int MAX_VALUE = 6;
	protected static int[] statistics = null;
	
	public Dice() {
		if(statistics == null) {
			statistics = new int[MAX_VALUE-MIN_VALUE+1];
			
			for(int i = MIN_VALUE; i < MAX_VALUE; i++) {
				statistics[i % (MAX_VALUE - MIN_VALUE + 1)] = 0;
			}
		}
	}
	
	public int roll() {
		//select random value in given range
		value = (int)(Math.random() * 1000 % MAX_VALUE + MIN_VALUE);
		
		//record the selection of that value in the statistics
		statistics[value % (MAX_VALUE - MIN_VALUE + 1)]++;
		
		return value;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public static int[] getStatistics() {
		return statistics;
	}
}
