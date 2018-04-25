package com.neilwilcoxson.yahtzy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class MenuHelper extends JPanel implements ActionListener{
	private static final long serialVersionUID = -868522479001593589L;

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New Game")) {
			GUI.reset();
			Game.initGame();
		}else if(e.getActionCommand().equals("View Scores")) {
			
		}else if(e.getActionCommand().equals("Quit")) {
			System.exit(0);
		}else if(e.getActionCommand().equals("Help")) {
			
		}else if(e.getActionCommand().equals("About")) {
			
		}
		
	}

}
