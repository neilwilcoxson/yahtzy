package com.neilwilcoxson.yahtzy;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiceTest {
	protected static final int NUM_TESTS = 100000;
	
	Dice d = null;
	
	@BeforeEach
	void setup() {
		d = new Dice();
	}
	
	@Test
	void range() {
		for(int i = 0; i < NUM_TESTS; i++) {
			d.roll();
			
			assertTrue(d.getValue() <= 6);
			assertTrue(d.getValue() >= 1);
		}
	}

	@Test
	void distribution() {
		for(int i = 0; i < NUM_TESTS; i++) {
			d.roll();
		}
		
		int[] stats = Dice.getStatistics();
		
		for(int i = 0; i < 6; i++) {
			System.out.println((i+1) + " was rolled " + stats[i] + " times.");
			for(int j = 0; j < 6; j++) {
				if(i != j) {
					assertTrue(Math.abs(stats[i] - stats[j]) < 1000);
				}
			}
		}
	}
}
