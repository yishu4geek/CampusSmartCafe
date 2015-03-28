package edu.scu.oop.proj.view;

import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.event.*;

//ContentPanel serves as the primary background panel for the right section of the GUI 
public class ContentPanel extends JPanel{
    private static final String MENUPANEL = "menu panel";
    MapPanel mapPanel;
    MenuPanel menuPanel;
    ViewFundPanel fundPanel;
    ViewCaloriePanel viewCaloriePanel;

    public ContentPanel() {
        super();
        setPreferredSize(new Dimension(1000, 1200));
        BackgroundPanel backgroundPanel = new BackgroundPanel(); 
        BorderLayout layout = new BorderLayout(0, 1); 
        setLayout(layout);
        this.add(backgroundPanel); 
    }
}

