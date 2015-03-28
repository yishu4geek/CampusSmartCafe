package edu.scu.oop.proj.view;


/**
 * Created by jiachenduan on 3/3/15.
 */
//import com.sun.tools.example.debug.gui.GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.event.*;

//the main frame for the GUI 
public class MainFrame extends JFrame{
    HomebuttonPanel homebuttonPanel;
    ContentPanel contentPanel;

    public MainFrame() {
        super();
        contentPanel = new ContentPanel();
        homebuttonPanel = new HomebuttonPanel();

        setSize(1200, 1200);
        setLayout(new BorderLayout());
        GUIManager.getInstance().setHomebutton_panel(homebuttonPanel);
        GUIManager.getInstance().setContent_panel(contentPanel);

        this.add(homebuttonPanel, BorderLayout.WEST);
        this.add(contentPanel, BorderLayout.EAST);

    }
    
    public void repaintMainFrame() {
    	contentPanel = new ContentPanel();
        homebuttonPanel = new HomebuttonPanel();
        setSize(1200, 1200);
        setLayout(new BorderLayout());
        GUIManager.getInstance().setHomebutton_panel(homebuttonPanel);
        GUIManager.getInstance().setContent_panel(contentPanel);

        this.add(homebuttonPanel, BorderLayout.WEST);
        this.add(contentPanel, BorderLayout.EAST);
    }



}
