package com.neilwilcoxson.yahtzy;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JPanel implements ActionListener{
	private static final long serialVersionUID = -5934960568302094412L;
	
	//constant values
	protected static final String WINDOW_TITLE = "Yahtzy";
	
	//Window UI Elements
	protected static JFrame mainFrame = null;
	protected static GridLayout mainWindowLayout = null;
	protected static GUI mainGUI = null;
	protected static MenuHelper menuHelper = null;
	
	//Menu bar UI Elements
	protected static JMenuBar menuBar = null;
	protected static JMenu gameMenu = null;
	protected static JMenuItem newGameOption = null;
	protected static JMenuItem highScoresOption = null;
	protected static JMenuItem quitOption = null;
	
	protected static JMenu helpMenu = null;
	protected static JMenuItem helpOption = null;
	protected static JMenuItem aboutOption = null;
	
	//Scorecard UI Elements
	protected static JButton[] scoreButtons = null;
	protected static JTextField[] scoreFields = null;
	protected static JPanel scorecardPanel = null;
	protected static GridLayout scorecardLayout = null;
	
	//Player info UI Elements
	protected static JPanel sidePanel = null;
	protected static GridLayout sidePanelLayout = null;
	protected static JLabel playerName = null;
	protected static JLabel playerTurn = null;
	
	//Dice UI Elements
	protected static JPanel dicePanel = null;
	protected static GridLayout dicePanelLayout = null;
	protected static JCheckBox[] diceKeepBoxes = null;
	protected static JTextField[] diceDisplay = null;
	protected static JButton rollButton = null;
	
	public static void draw() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				doDrawGUI();
			}
		});
	}
	
	protected static void doDrawGUI() {
		drawWindow();
		drawScorecard();
		drawPlayerDetails();
		drawDice();
		
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	protected static void drawWindow() {
		mainGUI = new GUI();
		menuHelper = new MenuHelper();
		
		mainFrame = new JFrame(WINDOW_TITLE);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setPreferredSize(new Dimension(200,20));
		mainFrame.setJMenuBar(menuBar);
		
		gameMenu = new JMenu("Game");
		
		newGameOption = new JMenuItem("New Game");
		newGameOption.setMnemonic(KeyEvent.VK_N);
		newGameOption.setActionCommand("New Game");
		newGameOption.addActionListener(menuHelper);
		gameMenu.add(newGameOption);
		
		highScoresOption = new JMenuItem("View High Scores");
		highScoresOption.setActionCommand("View Scores");
		highScoresOption.addActionListener(menuHelper);
		gameMenu.add(highScoresOption);
		
		menuBar.add(gameMenu);
		
		quitOption = new JMenuItem("Quit");
		quitOption.setMnemonic(KeyEvent.VK_Q);
		quitOption.setActionCommand("Quit");
		quitOption.addActionListener(menuHelper);
		gameMenu.add(quitOption);
		
		helpMenu = new JMenu("Help");
		
		helpOption = new JMenuItem("Help");
		helpOption.setMnemonic(KeyEvent.VK_F1);
		helpOption.setActionCommand("Help");
		helpOption.addActionListener(menuHelper);
		helpMenu.add(helpOption);
		
		aboutOption = new JMenuItem("About");
		aboutOption.setActionCommand("About");
		aboutOption.addActionListener(menuHelper);
		helpMenu.add(aboutOption);
		
		menuBar.add(helpMenu);
		
		mainGUI = new GUI();
		mainGUI.setOpaque(true);
		mainFrame.setContentPane(mainGUI);
		mainWindowLayout = new GridLayout(0,2);
		mainFrame.setLayout(mainWindowLayout);
	}
	
	protected static void drawScorecard() {
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
			scoreButtons[i].addActionListener(mainGUI);
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
		
		mainFrame.add(scorecardPanel);
	}
	
	protected static void drawPlayerDetails() {
		sidePanel = new JPanel();
		sidePanelLayout = new GridLayout(2,0);
		sidePanelLayout.setHgap(10);
		sidePanelLayout.setVgap(10);
		sidePanel.setLayout(sidePanelLayout);
		
		playerName = new JLabel();
		sidePanel.add(playerName);
		
		playerTurn = new JLabel();
		sidePanel.add(playerTurn);
	}
	
	protected static void drawDice() {
		dicePanel = new JPanel();
		dicePanelLayout = new GridLayout(0,2);
		dicePanelLayout.setHgap(15);
		dicePanelLayout.setVgap(15);
		dicePanel.setLayout(dicePanelLayout);
		
		diceDisplay = new JTextField[Game.NUM_DICE];
		diceKeepBoxes = new JCheckBox[Game.NUM_DICE];
		
		for(int i = 0; i < Game.NUM_DICE; i++) {
			diceKeepBoxes[i] = new JCheckBox("Keep");
			diceKeepBoxes[i].setActionCommand(Integer.toString(i));
			diceKeepBoxes[i].setSelected(false);
			diceKeepBoxes[i].addActionListener(mainGUI);
			dicePanel.add(diceKeepBoxes[i]);
			
			diceDisplay[i] = new JTextField();
			diceDisplay[i].setEditable(false);
			diceDisplay[i].setColumns(5);
			diceDisplay[i].setText(Integer.toString(Game.getDiceValues()[i]));
			dicePanel.add(diceDisplay[i]);
		}
		
		rollButton = new JButton("Roll");
		rollButton.setActionCommand("Roll");
		rollButton.setVerticalTextPosition(AbstractButton.CENTER);
		rollButton.setHorizontalTextPosition(AbstractButton.LEADING);
		rollButton.addActionListener(mainGUI);
		dicePanel.add(rollButton);
		
		sidePanel.add(dicePanel);
		mainFrame.add(sidePanel);
	}
	
	protected static void updateTotals() {
		for(int i : Scorecard.TOTALS) {
			scoreFields[i].setText(Integer.toString(Game.getScore(i)));
		}
	}
	
	protected static void reset() {
		for(JTextField t : scoreFields) {
			t.setText("");
		}
		
		for(JButton b : scoreButtons) {
			b.setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//check if player is keeping dice
		if(e.getActionCommand().matches("[0-9]+")) {
			int num = Integer.parseInt(e.getActionCommand());
			if(num < Game.NUM_DICE) {
				Game.setKeep(num);
			}
		}
		//check if player is rolling dice
		else if(e.getActionCommand().equals("Roll")) {
			Game.roll();
			
			int[] diceVals = Game.getDiceValues();
			
			for(int i = 0; i < Game.NUM_DICE; i++) {
				diceDisplay[i].setText(Integer.toString(diceVals[i]));
			}
		}
		//player must be selecting a category
		else {
			for(int i = Scorecard.ACES; i <= Scorecard.GRAND_TOTAL; i++) {
				if(e.getActionCommand().equals(Scorecard.CATEGORY_NAMES[i])) {
					Game.recordScore(i);
					scoreButtons[i].setEnabled(false);
					scoreFields[i].setText(Integer.toString(Game.getScore(i)));
					updateTotals();
					break;
				}
			}
		}
		
		switch(Game.getState()) {
		case Game.MUST_ROLL:
			rollButton.setEnabled(true);
			
			for(int i : Scorecard.SCORING_CATEGORIES) {
				scoreButtons[i].setEnabled(false);
			}
			
			for(int i = 0; i < Game.NUM_DICE; i++) {
				diceKeepBoxes[i].setSelected(false);
			}
			break;
		case Game.MAY_SCORE:
			rollButton.setEnabled(true);
			
			int[] availableCategories = Game.getAvailableCategories();
			
			for(int i : availableCategories) {
				scoreButtons[i].setEnabled(true);
			}
			break;
		case Game.MUST_SCORE:
			rollButton.setEnabled(false);
			break;
		case Game.GAME_OVER:
			rollButton.setEnabled(false);
			
			for(int i = 0; i < Game.NUM_DICE; i++) {
				diceKeepBoxes[i].setSelected(false);
			}
			
			System.out.println("Dice statistics:");
			for(int i = 0; i < 6; i++) {
				System.out.println((i+1) + ": " + Dice.getStatistics()[i]);
			}
			
			break;
		}
	}
}
