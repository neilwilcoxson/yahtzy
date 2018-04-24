package com.neilwilcoxson.yahtzy;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Game extends JPanel implements ActionListener{
	private static final long serialVersionUID = 8677862908952536072L;
	
	protected static String[] buttonLabels = {"Aces", "Twos", "Threes", "Fours", "Fives", "Sixes",
			"Upper Subtotal", "Bonus", "Upper Total", "Three of a Kind", "Four of a Kind", "Full House",
			"Small Straight", "Large Straight", "Yahtzy", "Yahtzy Bonus", "Chance", "Lower Total", "Grand Total"};
	protected static JButton[] buttons = new JButton[Scorecard.GRAND_TOTAL + 1];
	
	protected static JTextField[] fields = new JTextField[Scorecard.GRAND_TOTAL + 1];
	
	protected static void showGUI() {
		//frame
		JFrame frame = new JFrame("Yahtzy");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setOpaque(true);
		menuBar.setPreferredSize(new Dimension(200,20));
		frame.setJMenuBar(menuBar);
		
		//content pane
		Game game = new Game();
		game.setOpaque(true);
		frame.setContentPane(game);
		frame.setLayout(new GridLayout(0,2));
		
		//panels
		JPanel scorecardPanel = new JPanel();
		GridLayout layout = new GridLayout(0,2);
		layout.setHgap(5);
		layout.setVgap(5);
		scorecardPanel.setLayout(layout);
		
		JPanel sidePannel = new JPanel();
		layout = new GridLayout(2,0);
		layout.setHgap(10);
		layout.setVgap(10);
		sidePannel.setLayout(layout);
		
		JPanel dicePanel = new JPanel();
		layout = new GridLayout(5,0);
		layout.setHgap(15);
		layout.setVgap(15);
		dicePanel.setLayout(layout);
		
		//buttons and fields
		for(int i = Scorecard.ACES; i <= Scorecard.GRAND_TOTAL; i++) {
			buttons[i] = new JButton(buttonLabels[i]);
			buttons[i].setActionCommand(buttonLabels[i]);
			buttons[i].setVerticalTextPosition(AbstractButton.CENTER);
			buttons[i].setHorizontalTextPosition(AbstractButton.LEADING);
			buttons[i].setMnemonic(KeyEvent.VK_D);
			buttons[i].addActionListener(game);
			scorecardPanel.add(buttons[i]);
			
			fields[i] = new JTextField();
			fields[i].setEditable(false);
			fields[i].setColumns(5);
			fields[i].setText("3");
			scorecardPanel.add(fields[i]);
			
			//create division between upper and lower sections
			if(i == Scorecard.UPPER_TOTAL) {
				scorecardPanel.add(new JLabel(""));
				scorecardPanel.add(new JLabel(""));
			}
		}
		
		//dice
		
		//labels
		JLabel header = new JLabel("", JLabel.CENTER);
		header.setText("Bob's Turn");
		sidePannel.add(header);
		
		sidePannel.add(dicePanel);
		
		frame.add(scorecardPanel);
		frame.add(sidePannel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				showGUI();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("Aces".equals(e.getActionCommand())) {
			System.out.println("Aces");
		}else if("Twos".equals(e.getActionCommand())) {
			System.out.println("Twos");
		}
		
		
	}

}
