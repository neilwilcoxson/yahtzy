package com.neilwilcoxson.yahtzy;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MenuHelper extends JPanel implements ActionListener{
	private static final long serialVersionUID = -868522479001593589L;
	
	protected static JTable hsTable = null;
	
	public static void drawHSTable(Object[][] data) {
		String[] columnNames = {"Name", "Score"};
		
		hsTable = new JTable(data, columnNames);
		hsTable.setPreferredScrollableViewportSize(new Dimension(200,300));
		hsTable.setFillsViewportHeight(true);
		
		JScrollPane scrollPane = new JScrollPane(hsTable);
		
		JOptionPane.showMessageDialog(null, scrollPane, "Previous Scores", JOptionPane.DEFAULT_OPTION);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New Game")) {
			Game.initGame();
			GUI.reset();
		}else if(e.getActionCommand().equals("View Scores")) {
			drawHSTable(Game.getHighScores());
		}else if(e.getActionCommand().equals("Quit")) {
			System.exit(0);
		}
		
	}

}
