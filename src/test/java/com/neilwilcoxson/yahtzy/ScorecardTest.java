package com.neilwilcoxson.yahtzy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScorecardTest {
	Dice[] dice = null;
	Scorecard scorecard = null;
	
	@BeforeEach
	void setup() {
		dice = new Dice[5];
		
		for(int i = 0; i < 5; i++) {
			dice[i] = new Dice();
		}
		
		scorecard = new Scorecard();
	}

	@Test
	void allAces() {
		for(Dice d : dice) {
			d.setValue(1);
		}
		
		scorecard.commit(Scorecard.ACES, dice);
		assertEquals(scorecard.getScore(Scorecard.ACES), 5);
	}
	
	@Test
	void noAces() {
		for(Dice d : dice) {
			d.setValue(2);
		}
		
		scorecard.commit(Scorecard.ACES, dice);
		assertEquals(scorecard.getScore(Scorecard.ACES), 0);
	}
	
	@Test
	void someAces() {
		for(int i = 0; i < 2; i++) {
			dice[i].setValue(1);
		}
		for(int i = 2; i < 5; i++) {
			dice[i].setValue(6);
		}
		
		scorecard.commit(Scorecard.ACES, dice);
		assertEquals(scorecard.getScore(Scorecard.ACES), 2);
	}
	
	@Test
	void allThrees() {
		for(Dice d : dice) {
			d.setValue(3);
		}
		
		scorecard.commit(Scorecard.THREES, dice);
		assertEquals(scorecard.getScore(Scorecard.THREES), 15);
	}
	
	@Test
	void someThrees() {
		for(int i = 0; i < 3; i++) {
			dice[i].setValue(3);
		}
		for(int i = 3; i < 5; i++) {
			dice[i].setValue(6);
		}
		
		scorecard.commit(Scorecard.THREES, dice);
		assertEquals(scorecard.getScore(Scorecard.THREES), 9);
	}
	
	@Test
	void noThrees() {
		for(Dice d : dice) {
			d.setValue(1);
		}
		
		scorecard.commit(Scorecard.THREES, dice);
		assertEquals(scorecard.getScore(Scorecard.THREES), 0);
	}
	
	@Test
	void threeKind() {
		for(int i = 0; i < 3; i++) {
			dice[i].setValue(4);
		}
		
		for(int i = 3; i < 5; i++) {
			dice[i].setValue(1);
		}
		
		scorecard.commit(Scorecard.THREE_OF_A_KIND, dice);
		assertEquals(scorecard.getScore(Scorecard.THREE_OF_A_KIND), 14);
	}
	
	@Test
	void fourKind() {
		for(int i = 0; i < 4; i++) {
			dice[i].setValue(5);
		}
		
		dice[4].setValue(1);
		
		scorecard.commit(Scorecard.FOUR_OF_A_KIND, dice);
		assertEquals(scorecard.getScore(Scorecard.FOUR_OF_A_KIND), 21);
	}
	
	@Test
	void notFourKind(){
		for(int i = 0; i < 5; i++) {
			dice[i].setValue(i+1);
		}
		
		scorecard.commit(Scorecard.FOUR_OF_A_KIND, dice);
		assertEquals(scorecard.getScore(Scorecard.FOUR_OF_A_KIND), 0);
	}
	
	@Test
	void fullHouseLowerBig() {
		for(int i = 0; i < 3; i++) {
			dice[i].setValue(2);
		}
		for(int i = 3; i < 5; i++) {
			dice[i].setValue(3);
		}
		
		scorecard.commit(Scorecard.FULL_HOUSE, dice);
		assertEquals(scorecard.getScore(Scorecard.FULL_HOUSE), FullHouse.CATEGORY_VALUE);
	}
	
	@Test
	void fullHouseUpperBig() {
		for(int i = 0; i < 2; i++) {
			dice[i].setValue(2);
		}
		for(int i = 2; i < 5; i++) {
			dice[i].setValue(3);
		}
		
		scorecard.commit(Scorecard.FULL_HOUSE, dice);
		assertEquals(scorecard.getScore(Scorecard.FULL_HOUSE), FullHouse.CATEGORY_VALUE);
	}
	
	@Test
	void notFullHouse() {
		for(int i = 0; i < 3; i++) {
			dice[i].setValue(2);
		}
		for(int i = 3; i < 5; i++) {
			dice[i].setValue(i);
		}
		
		scorecard.commit(Scorecard.FULL_HOUSE, dice);
		assertEquals(scorecard.getScore(Scorecard.FULL_HOUSE), 0);
	}
	
	@Test
	void yahtzyAsFullHouse() {
		for(int i = 0; i < 5; i++) {
			dice[i].setValue(5);
		}
		
		scorecard.commit(Scorecard.FULL_HOUSE, dice);
		assertEquals(scorecard.getScore(Scorecard.FULL_HOUSE), 0);
	}
	
	@Test
	void smallStraightLower() {
		for(int i = 0; i < 4; i++) {
			dice[i].setValue(i+1);
		}
		dice[4].setValue(1);
		
		scorecard.commit(Scorecard.SMALL_STRAIGHT, dice);
		assertEquals(scorecard.getScore(Scorecard.SMALL_STRAIGHT), Straight.SMALL_STRAIGHT_VALUE);
	}
	
	@Test
	void smallStraightUpper() {
		for(int i = 1; i < 5; i++) {
			dice[i].setValue(i);
		}
		dice[0].setValue(1);
		
		scorecard.commit(Scorecard.SMALL_STRAIGHT, dice);
		assertEquals(scorecard.getScore(Scorecard.SMALL_STRAIGHT), Straight.SMALL_STRAIGHT_VALUE);
	}
	
	@Test
	void almostSmallStraight() {
		dice[0].setValue(1);
		dice[1].setValue(2);
		dice[2].setValue(3);
		dice[3].setValue(5);
		dice[4].setValue(6);
		
		scorecard.commit(Scorecard.SMALL_STRAIGHT, dice);
		assertEquals(scorecard.getScore(Scorecard.SMALL_STRAIGHT), 0);
	}
	
	@Test
	void largeStraight() {
		for(int i = 0; i < 5; i++) {
			dice[i].setValue(i+2);
		}
		
		scorecard.commit(Scorecard.LARGE_STRAIGHT, dice);
		assertEquals(scorecard.getScore(Scorecard.LARGE_STRAIGHT), Straight.LARGE_STRAIGHT_VALUE);
	}
	
	@Test
	void almostLargeStraight() {
		for(int i = 0; i < 4; i++) {
			dice[i].setValue(i+1);
		}
		dice[4].setValue(6);
		
		scorecard.commit(Scorecard.LARGE_STRAIGHT, dice);
		assertEquals(scorecard.getScore(Scorecard.LARGE_STRAIGHT), 0);
	}
	
	@Test
	void yahtzy() {
		for(int i = 0; i < 5; i++) {
			dice[i].setValue(5);
		}
		
		scorecard.commit(Scorecard.YAHTZY, dice);
		assertEquals(scorecard.getScore(Scorecard.YAHTZY), Yahtzy.CATEGORY_VALUE);
	}
	
	@Test
	void notYahtzy() {
		for(int i = 0; i < 4; i++) {
			dice[i].setValue(5);
		}
		
		dice[4].setValue(6);
		
		scorecard.commit(Scorecard.YAHTZY, dice);
		assertEquals(scorecard.getScore(Scorecard.YAHTZY), 0);
	}
	
	@Test
	void yahtzyBonus() {
		for(int i = 0; i < 5; i++) {
			dice[i].setValue(5);
		}
		
		scorecard.commit(Scorecard.YAHTZY_BONUS, dice);
		assertEquals(scorecard.getScore(Scorecard.YAHTZY_BONUS), YahtzyBonus.CATEGORY_VALUE);
		scorecard.commit(Scorecard.YAHTZY_BONUS, dice);
		assertEquals(scorecard.getScore(Scorecard.YAHTZY_BONUS), 2*YahtzyBonus.CATEGORY_VALUE);
	}
	
	@Test
	void chance() {
		for(int i = 0; i < 5; i++) {
			dice[i].setValue(i+1);
		}
		
		scorecard.commit(Scorecard.CHANCE, dice);
		assertEquals(scorecard.getScore(Scorecard.CHANCE), 1+2+3+4+5);
	}
	
	@Test
	void upperBonusTotal() {
		assertEquals(scorecard.getScore(Scorecard.UPPER_SUBTOTAL), 0);
		assertEquals(scorecard.getScore(Scorecard.UPPER_BONUS), 0);
		assertEquals(scorecard.getScore(Scorecard.UPPER_TOTAL), 0);
		
		for(int i = Scorecard.ACES; i <= Scorecard.SIXES; i++) {
			for(int j = 0; j < 3; j++) {
				dice[j].setValue(i+1);
			}
			for(int j = 3; j < 5; j++) {
				dice[j].setValue((i + 2 > 6) ? 1 : (i + 2));
			}
			scorecard.commit(i, dice);
		}
		
		assertEquals(scorecard.getScore(Scorecard.UPPER_SUBTOTAL), 63);
		assertEquals(scorecard.getScore(Scorecard.UPPER_BONUS), UpperBonus.CATEGORY_VALUE);
		assertEquals(scorecard.getScore(Scorecard.UPPER_TOTAL), 63 + UpperBonus.CATEGORY_VALUE);
	}
	
	@Test
	void lowerTotal() {
		assertEquals(scorecard.getScore(Scorecard.LOWER_TOTAL), 0);
		
		for(int i = 0; i < 5; i++) {
			dice[i].setValue(5);
		}

		scorecard.commit(Scorecard.THREE_OF_A_KIND, dice); //25
		scorecard.commit(Scorecard.FOUR_OF_A_KIND, dice);  //25
		scorecard.commit(Scorecard.YAHTZY, dice);          //50
		scorecard.commit(Scorecard.YAHTZY_BONUS, dice);    //100
		scorecard.commit(Scorecard.CHANCE, dice);          //25
		
		for(int i = 0; i < 3; i++) {
			dice[i].setValue(4);
		}
		
		scorecard.commit(Scorecard.FULL_HOUSE, dice);      //25
		
		for(int i = 0; i < 5; i++) {
			dice[i].setValue(i+1);
		}
		
		scorecard.commit(Scorecard.SMALL_STRAIGHT, dice);  //30
		scorecard.commit(Scorecard.LARGE_STRAIGHT, dice);  //40
		
		assertEquals(scorecard.getScore(Scorecard.LOWER_TOTAL), 320);
	}
	
	@Test
	void grandTotal() {
		assertEquals(scorecard.getScore(Scorecard.GRAND_TOTAL), 0);
		
		upperBonusTotal();
		lowerTotal();
		
		assertEquals(scorecard.getScore(Scorecard.GRAND_TOTAL), 320+63+35);
	}

}
