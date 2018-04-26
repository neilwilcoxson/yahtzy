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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = -5934960568302094412L;
	
	//constant values
	protected static final String WINDOW_TITLE = "Yahtzy";
	
	//Main Window UI Elements
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
	
	//Player info UI Elements
	protected static JPanel sidePanel = null;
	protected static GridLayout sidePanelLayout = null;
	protected static JLabel playerTurn = null;
	
	//Dice UI Elements
	protected static JPanel dicePanel = null;
	protected static GridLayout dicePanelLayout = null;
	protected static JCheckBox[] diceKeepBoxes = null;
	protected static JTextField[] diceDisplay = null;
	protected static JButton rollButton = null;
	
	//Scorecard Display
	protected static ScorecardWindow[] scorecards = null;
	
	public static void draw() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				doDrawGUI();
			}
		});
	}
	
	public static void config() {
		int numPlayers = 0;
		
		do {
			String s = JOptionPane.showInputDialog("Enter number of players");
			numPlayers = Integer.parseInt(s);
		}while(numPlayers <= 0);
		
		JPanel namePromptPanel = new JPanel(new GridLayout(0,1));
		JTextField[] nameEntries = new JTextField[numPlayers];
		
		for(int i = 0; i < numPlayers; i++) {
			namePromptPanel.add(nameEntries[i]);
		}
		
		//TODO resume here
	}
	
	protected static void doDrawGUI() {
		drawWindow();
		drawPlayerDetails();
		drawDice();
		mainFrame.pack();
		
		scorecards = new ScorecardWindow[Game.getNumPlayers()];
		
		for(int i = 0; i < scorecards.length; i++) {
			scorecards[i] = new ScorecardWindow(Game.getPlayerName(i));
			scorecards[i].drawScorecard(i*300, mainFrame.getHeight());
		}

		mainFrame.setVisible(true);
	}
	
	protected static void drawWindow() {
		mainGUI = new GUI();
		menuHelper = new MenuHelper();
		
		mainFrame = new JFrame(WINDOW_TITLE);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setPreferredSize(new Dimension(100,20));
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
		mainWindowLayout = new GridLayout();
		mainFrame.setLayout(mainWindowLayout);
	}
	
	protected static void drawPlayerDetails() {
		sidePanel = new JPanel();
		sidePanelLayout = new GridLayout(0,1);
		sidePanelLayout.setHgap(10);
		sidePanelLayout.setVgap(10);
		sidePanel.setLayout(sidePanelLayout);
		
		playerTurn = new JLabel();
		playerTurn.setText(Game.getPlayerName() + "'s Turn, Rolls Remaining: " + Game.getRollsRemaining());
		sidePanel.add(playerTurn);
	}
	
	protected static void drawDice() {
		dicePanel = new JPanel();
		dicePanelLayout = new GridLayout(2,6);
		dicePanelLayout.setHgap(15);
		dicePanelLayout.setVgap(5);
		dicePanel.setLayout(dicePanelLayout);
		
		diceDisplay = new JTextField[Game.NUM_DICE];
		diceKeepBoxes = new JCheckBox[Game.NUM_DICE];
		
		for(int i = 0; i < Game.NUM_DICE; i++) {
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
		
		for(int i = 0; i < Game.NUM_DICE; i++) {
			diceKeepBoxes[i] = new JCheckBox("Keep");
			diceKeepBoxes[i].setActionCommand(Integer.toString(i));
			diceKeepBoxes[i].setSelected(false);
			diceKeepBoxes[i].addActionListener(mainGUI);
			dicePanel.add(diceKeepBoxes[i]);
		}
		
		sidePanel.add(dicePanel);
		mainFrame.add(sidePanel);
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
		
		if(!e.getActionCommand().equals("Force Update")) {
			scorecards[Game.getCurrentPlayerID()].update();
		}
		
		playerTurn.setText(Game.getPlayerName() + "'s Turn, Rolls Remaining: " + Game.getRollsRemaining());
		
		switch(Game.getState(Game.getCurrentPlayerID())) {
		case Game.MUST_ROLL:
			rollButton.setEnabled(true);
			
			for(int i = 0; i < Game.NUM_DICE; i++) {
				diceKeepBoxes[i].setSelected(false);
			}
			break;
			
		case Game.MAY_SCORE:
			rollButton.setEnabled(true);
			break;
			
		case Game.MUST_SCORE:
			rollButton.setEnabled(false);
			playerTurn.setText(Game.getPlayerName() + "'s Turn, Rolls Remaining: 0");
			break;
			
		case Game.GAME_OVER:
			rollButton.setEnabled(false);
			playerTurn.setText("Game Over");
			
			for(int i = 0; i < Game.NUM_DICE; i++) {
				diceKeepBoxes[i].setSelected(false);
			}
			
			Game.saveHighScores();
			
			System.out.println("Dice statistics:");
			for(int i = 0; i < 6; i++) {
				System.out.println((i+1) + ": " + Dice.getStatistics()[i]);
			}
			
			break;
			
		}
	}
	
	public static void reset() {
		rollButton.setEnabled(true);
		
		for(int i = 0; i < Game.NUM_DICE; i++) {
			diceKeepBoxes[i].setSelected(false);
		}
		
		for(int i = 0; i < scorecards.length; i++) {
			scorecards[i].reset();
		}
	}
	
	public static void update() {
		mainGUI.actionPerformed(new ActionEvent(mainGUI, 0,"Force Update"));
	}
}
