package com.neilwilcoxson.yahtzy;

public class Game {
	public static final int NUM_DICE = 5;
	
	protected static Player[] players = null;
	protected static Dice[] dice = null;
	
//	protected static void showGUI() {				
//		
//		//labels
//		JLabel header = new JLabel("", JLabel.CENTER);
//		sidePannel.add(header);
//		
//		JLabel turn = new JLabel("", JLabel.CENTER);
//		sidePannel.add(turn);
	
	public static void main(String[] args) {
		GUI.draw();
	}

}
