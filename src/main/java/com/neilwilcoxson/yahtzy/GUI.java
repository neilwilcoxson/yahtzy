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
import javax.swing.JScrollPane;
import javax.swing.JTable;
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
	protected static JTable diceTable = null;
	
	//Scorecard Display
	protected static ScorecardWindow[] scorecards = null;
	
	public static void draw() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				doDrawGUI();
			}
		});
	}
	
	public static String[] config() {
		String[] result = null;
		
		JPanel namePromptPanel = null;
		JTextField[] nameEntries = null;
		
		int confirm;
		int numPlayers = 0;
		String s;
		
		do {
			do {
				numPlayers = 0;
				s = JOptionPane.showInputDialog("Enter number of players");
				if(s != null && s.matches("[0-9]+")) {
					numPlayers = Integer.parseInt(s);
				}
			}while(numPlayers <= 0);
			
			namePromptPanel = new JPanel(new GridLayout(0,1));
			nameEntries = new JTextField[numPlayers];
			
			for(int i = 0; i < numPlayers; i++) {
				nameEntries[i] = new JTextField();
				nameEntries[i].setColumns(20);
				nameEntries[i].setEditable(true);
				namePromptPanel.add(nameEntries[i]);
			}
			
			confirm = JOptionPane.showConfirmDialog(null, namePromptPanel, "Enter player name(s)", JOptionPane.OK_CANCEL_OPTION);
			
		}while(confirm == JOptionPane.CANCEL_OPTION);
		
		result = new String[numPlayers+1];
		result[0] = s;
		
		for(int i = 1; i <= numPlayers; i++) {
			result[i] = nameEntries[i-1].getText();
		}
		
		return result;
	}
	
	protected static void doDrawGUI() {
		drawWindow();
		drawPlayerDetails();
		drawDice();
		
		mainFrame.pack();
		
		drawScorecards();
		
		mainFrame.setVisible(true);
	}
	
	public static void drawScorecards(){
		scorecards = new ScorecardWindow[Game.getNumPlayers()];
		
		for(int i = 0; i < scorecards.length; i++) {
			scorecards[i] = new ScorecardWindow(Game.getPlayerName(i));
			scorecards[i].drawScorecard(i*300, mainFrame.getHeight());
		}
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
		playerTurn.setHorizontalAlignment(JLabel.CENTER);
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
			diceDisplay[i].setEnabled(false);
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
	
	protected static void displayDiceStatistics() {
		//display dice statistics
		int[] stats = Dice.getStatistics();
		Object[][] data = new Object[stats.length][2];
		
		for(int i = 0; i < stats.length; i++) {
			data[i][0] = i+1;
			data[i][1] = stats[i];
		}
		
		String[] columnNames = {"Value", "Number of Occurrences"};
		
		diceTable = new JTable(data, columnNames);
		diceTable.setPreferredScrollableViewportSize(new Dimension(325,100));
		diceTable.setFillsViewportHeight(true);
		
		JScrollPane scrollPane = new JScrollPane(diceTable);
		
		JOptionPane.showMessageDialog(null, scrollPane, "Dice Statistics", JOptionPane.DEFAULT_OPTION);
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
				diceDisplay[i].setEnabled(false);
				diceKeepBoxes[i].setSelected(false);
			}
			break;
			
		case Game.MAY_SCORE:
			rollButton.setEnabled(true);
			scorecards[Game.getCurrentPlayerID()].showOptions();
			
			for(int i = 0; i < Game.NUM_DICE; i++) {
				diceDisplay[i].setEnabled(true);
			}
			break;
			
		case Game.MUST_SCORE:
			rollButton.setEnabled(false);
			playerTurn.setText(Game.getPlayerName() + "'s Turn, Rolls Remaining: 0");
			scorecards[Game.getCurrentPlayerID()].showOptions();
			break;
			
		case Game.GAME_OVER:
			rollButton.setEnabled(false);
			playerTurn.setText("Game Over");
			
			for(int i = 0; i < Game.NUM_DICE; i++) {
				diceKeepBoxes[i].setSelected(false);
			}
			
			Game.saveHighScores();
			GUI.displayDiceStatistics();
			
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
