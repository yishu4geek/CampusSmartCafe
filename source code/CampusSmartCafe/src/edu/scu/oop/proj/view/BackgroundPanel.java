package edu.scu.oop.proj.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

//BackgroundPanel holds the welcome panel and MapPanel
public class BackgroundPanel extends JPanel {
	
	public BackgroundPanel() {
		setPreferredSize(new Dimension(1000, 1200));
		MapPanel mapPanel = new MapPanel();
		setLayout(new BorderLayout()); 
		JLabel welcome = new JLabel ("<html><br>Welcome to Campus Smart Cafe<br><br></html>"); 
	    Font font = welcome.getFont(); 
	    welcome.setFont(new Font(font.getFontName(), font.getStyle(), 36));
	    welcome.setForeground(new Color(96, 96, 96));
	    welcome.setHorizontalAlignment(JLabel.CENTER);
	    this.add(welcome, BorderLayout.PAGE_START); 
	    this.add(mapPanel);
	}
}
