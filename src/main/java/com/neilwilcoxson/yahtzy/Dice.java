package com.neilwilcoxson.yahtzy;

public class Dice {
	protected int value;
	protected boolean keep;
	protected static final int MIN_VALUE = 1;
	protected static final int MAX_VALUE = 6;
	protected static int[] statistics = null;
	
	protected static Object mutex = new Object();
	
	public Dice() {
		synchronized(mutex){
			if(statistics == null) {
				int[] temp = new int[MAX_VALUE-MIN_VALUE+1];
				for(int i = 0; i < MAX_VALUE-MIN_VALUE+1; i++) {
					temp[i] = 0;
				}
				statistics = temp;
			}
		}
		
		value = 1;
		keep = false;
	}
	
	public int roll() {
		if(!keep) {
			//select random value in given range
			value = (int)(Math.random() * 1000 % MAX_VALUE + MIN_VALUE);
		
		//record the selection of that value in the statistics
		statistics[value % (MAX_VALUE - MIN_VALUE + 1)]++;
		}
		
		return value;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public void toggleKeep() {
		keep = !keep;
	}
	
	public void removeKeep() {
		keep = false;
	}
	
	public boolean getKeep() {
		return keep;
	}
	
	public static int[] getStatistics() {
		return statistics;
	}
	
	public static void reset() {
		synchronized(mutex) {
			for(int i = 0; i < MAX_VALUE-MIN_VALUE+1; i++) {
				statistics[i] = 0;
			}
		}
	}
}
