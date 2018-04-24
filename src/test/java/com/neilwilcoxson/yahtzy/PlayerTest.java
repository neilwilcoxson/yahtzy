package com.neilwilcoxson.yahtzy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {
	Player player = null;
	
	@BeforeEach
	void setup() {
		while(Player.getNumPlayers() != 0) {
			Player.removePlayer();
		}
	}

	@Test
	void defaultPlayer() {
		player = new Player();
		assertEquals(player.getName(), "Player 1");
	}
	
	@Test
	void customPlayer() {
		player = new Player("John Smith");
		assertEquals(player.getName(), "John Smith");
	}
	
	@Test
	void removePlayer() {
		assertEquals(Player.getNumPlayers(), 0);
		player = new Player();
		player = new Player();
		assertEquals(Player.getNumPlayers(), 2);
		Player.removePlayer();
		assertEquals(Player.getNumPlayers(), 1);
	}

}
