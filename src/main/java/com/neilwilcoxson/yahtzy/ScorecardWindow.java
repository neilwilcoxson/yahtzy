package com.neilwilcoxson.yahtzy;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ScorecardWindow extends JPanel implements ActionListener{
	private static final long serialVersionUID = 5663714488996350021L;
	
	protected String playerName = null;
	
	ScorecardWindow(String playerName){
		this.playerName = playerName;
	}
	
	//Scorecard UI Elements
	protected JFrame scorecardFrame = null;
	protected JButton[] scoreButtons = null;
	protected JTextField[] scoreFields = null;
	protected JPanel scorecardPanel = null;
	protected GridLayout scorecardLayout = null;
	
	protected void drawScorecard(int playerNumber) {
		scorecardFrame = new JFrame(playerName);
		scorecardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		scorecardPanel = new JPanel();
		scorecardLayout = new GridLayout(0,2);
		scorecardLayout.setHgap(5);
		scorecardLayout.setVgap(5);
		scorecardPanel.setLayout(scorecardLayout);
		
		scoreButtons = new JButton[Scorecard.GRAND_TOTAL + 1];
		scoreFields = new JTextField[Scorecard.GRAND_TOTAL + 1];
		
		for(int i = Scorecard.ACES; i <= Scorecard.GRAND_TOTAL; i++) {
			scoreButtons[i] = new JButton(Scorecard.CATEGORY_NAMES[i]);
			scoreButtons[i].setActionCommand(Scorecard.CATEGORY_NAMES[i]);
			scoreButtons[i].setVerticalTextPosition(AbstractButton.CENTER);
			scoreButtons[i].setHorizontalTextPosition(AbstractButton.LEADING);
			scoreButtons[i].setEnabled(false);
			scoreButtons[i].addActionListener(this);
			scorecardPanel.add(scoreButtons[i]);
			
			scoreFields[i] = new JTextField();
			scoreFields[i].setEditable(false);
			scoreFields[i].setColumns(5);
			scorecardPanel.add(scoreFields[i]);
			
			//create division between upper and lower sections
			if(i == Scorecard.UPPER_TOTAL) {
				scorecardPanel.add(new JLabel(""));
				scorecardPanel.add(new JLabel(""));
			}
		}
		
		scorecardFrame.add(scorecardPanel);
		scorecardFrame.pack();
		scorecardFrame.setVisible(true);
	}
	
	protected void updateTotals() {
		for(int i : Scorecard.TOTALS) {
			scoreFields[i].setText(Integer.toString(Game.getScore(i)));
		}
	}
	
	protected void reset() {
		for(JTextField t : scoreFields) {
			t.setText("");
		}
		
		for(JButton b : scoreButtons) {
			b.setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for(int i = Scorecard.ACES; i <= Scorecard.GRAND_TOTAL; i++) {
			if(e.getActionCommand().equals(Scorecard.CATEGORY_NAMES[i])) {
				Game.recordScore(i);
				scoreButtons[i].setEnabled(false);
				scoreFields[i].setText(Integer.toString(Game.getScore(i)));
				updateTotals();
				GUI.update();
				break;
			}
		}
	
		switch(Game.getState(Game.getCurrentPlayerID())) {
		case Game.WAITING:
		case Game.MUST_ROLL:			
			for(int i : Scorecard.SCORING_CATEGORIES) {
				scoreButtons[i].setEnabled(false);
			}
			break;
		case Game.MAY_SCORE:
			int[] availableCategories = Game.getAvailableCategories();
			
			for(int i : availableCategories) {
				scoreButtons[i].setEnabled(true);
			}
			break;
		case Game.MUST_SCORE:
			break;
		case Game.GAME_OVER:
			break;
			
		}
	}
	
	public void update() {
		actionPerformed(new ActionEvent(this, 0, "Force Update"));
	}
}
