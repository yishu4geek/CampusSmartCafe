package edu.scu.oop.proj.view;

import javax.swing.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import edu.scu.oop.proj.dao.DAOFactory;
import edu.scu.oop.proj.entity.UserSession;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;

//ViewCaloriePanel is the page display the Calories Manager
public class ViewCaloriePanel extends JPanel{
    JLabel l_available_cal, l_set_fund,l_funds, l_existing_setting, l_message; 
    JPanel l_preference, l_edit; 
    JButton b_edit, b_view_detailed_cal,b_back, b_save; 
    JTextArea t_set_fund,t_expense_list;
    JPanel settingfundPanel,buttonPanel,expensePanel,map_panel;
    int consumed = 100;
   
    public ViewCaloriePanel(){
        super();
        GridBagLayout layout = new GridBagLayout(); 
        setLayout(layout);
        GridBagConstraints gc = new GridBagConstraints(); 
        super.setSize(700, 700);
        setVisible(true);
        //**********************create setting calorie panel**************************************************
        
        settingfundPanel = new JPanel(new GridLayout(0,3));
        l_existing_setting = new JLabel(); 
        l_preference = new JPanel(); 
        l_edit = new JPanel(); 
        int calorie_preference = DAOFactory.getUserDAO().findCaloriesPreferenceById(UserSession.getInstance().getCurrentUser().getId()); 
        l_existing_setting.setText(String.valueOf(calorie_preference));
        t_set_fund = new JTextArea(2,15);      //  t_set_fund.setFont(labelFont_black);
        Border border = BorderFactory.createLineBorder(new Color(128, 128, 128));
        t_set_fund.setBorder(BorderFactory.createCompoundBorder(border, 
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
       
        l_funds = new JLabel();
        l_set_fund = new JLabel("Calories Preference/day:" ,SwingConstants.CENTER);
        Font font = l_set_fund.getFont();
        l_set_fund.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() ));
        l_available_cal = new JLabel("Available calories:",SwingConstants.CENTER);
        font = l_available_cal.getFont();
        l_available_cal.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() ));
        b_edit = new JButton("EDIT");
        b_save = new JButton("SAVE"); 
        l_message = new JLabel(); 
        
        //save the new preference 
        b_save.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent actionEvent) {
        		MainFrame frame = (MainFrame)SwingUtilities.getRoot(ViewCaloriePanel.this); 
        		int preference_setting = Integer.parseInt(t_set_fund.getText()); 
        		
        		//validate if the input value is negative 
        		if (preference_setting < 0) {
        			l_message.setText("ERROR : calories cannot be negative number");
        			
        		//validate if nothing is entered 
        		}else if (t_set_fund.getText() == null) {
        			l_message.setText("ERROR: please enter a number");
        			
        		//correct input, update the calories preference in database 
        		}else {
        			boolean updateCaloriesAvailable = ViewCaloriePanel.this.updateCaloriesAvailable(); 
        			if (updateCaloriesAvailable) {
	        			DAOFactory.getUserDAO().setCaloriesPreferenceById(UserSession.getInstance().getCurrentUser().getId(), preference_setting);
	        			l_preference.removeAll();
	        			l_preference.add(l_existing_setting); 
	        			l_existing_setting.setText(String.valueOf(preference_setting));
	        			l_edit.removeAll();
	                	l_edit.add(b_edit);
	                	l_message.setText("");
	                	frame.validate();
	    		        frame.setVisible(true);
        			}
        		}
        	}
        });
        
        //change the textField to update preference 
        b_edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            		
            	MainFrame frame = (MainFrame)SwingUtilities.getRoot(ViewCaloriePanel.this); 
            	l_preference.removeAll();
            	l_preference.add(t_set_fund); 
            	l_edit.removeAll();
            	l_edit.add(b_save); 
            	frame.validate();
            	frame.repaint();
		        frame.setVisible(true);
            }
        });


        settingfundPanel.add(l_set_fund);
        settingfundPanel.add(l_preference);
        l_preference.add(l_existing_setting); 
        settingfundPanel.add(l_edit);
        l_edit.add(b_edit); 
        settingfundPanel.add(l_available_cal);
        this.updateCaloriesAvailable(); 
        settingfundPanel.add(l_funds);
        settingfundPanel.add(l_message);
        add(settingfundPanel);
        gc.gridwidth = 0; 
        layout.setConstraints(settingfundPanel, gc);


        //**************************create button panel*******************************************************
        buttonPanel = new JPanel();
        b_view_detailed_cal = new JButton("See Consumption Detail");
        b_view_detailed_cal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                expensePanel.setVisible(true);
            }
        });
        b_back = new JButton("BACK");
        b_back.setActionCommand("BACK");
        b_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	MainFrame frame = (MainFrame)SwingUtilities.getRoot(ViewCaloriePanel.this);
                JPanel fp = GUIManager.getInstance().getContent_panel();
                fp.removeAll();
                fp.add(new BackgroundPanel());
                frame.validate();
                frame.repaint();
                frame.setVisible(true);
            }
        });
        buttonPanel.add(b_view_detailed_cal);
        buttonPanel.add(b_back);
        add(buttonPanel);
        gc.gridwidth = 0; 
        layout.setConstraints(buttonPanel, gc);

        //****************************create consumption List Panel********************************************************

        expensePanel  = new JPanel ();
        expensePanel.setLayout(new BorderLayout());
        ArrayList<ArrayList<Object> > items = DAOFactory.getUserDAO().getCaloriesHistory(UserSession.getInstance().getCurrentUser().getId());
        //there's no purchase history for today yet 
        if (items.size() == 0) {
        	JLabel noTranLabel = new JLabel("No purchase history yet"); 
        	expensePanel.add(noTranLabel, BorderLayout.CENTER); 
        	
        }
        //there are already purchase history, retrieve them and display in the table 
        else {
	        int numOfItems = items.size(); 
	        Object[][] foodItems = new Object[numOfItems][5]; 
	        for (int i = 0; i < numOfItems; i++) {
	        	int amount = 0; 
	        	for (int j = 0; j < 5; j++) {
	        		switch(j) {
	        			case 0: foodItems[i][j] = (Integer)items.get(i).get(j); 
	        					break;
	        			case 1: foodItems[i][j] = (String)items.get(i).get(j); 
	        					break;
	        			case 2: foodItems[i][j] = (Integer)items.get(i).get(j); 
	        					amount = (Integer)foodItems[i][j]; 
	        					if ((Integer)foodItems[i][j] == 0) {
	        						foodItems[i][j] = "N.A"; 
	        					}
	        					break;
	        			case 3: foodItems[i][j] = (Integer)items.get(i).get(j); 
			        			if (amount > 1) {
		    						foodItems[i][j] = (Integer)foodItems[i][j] * amount; 
		    					}
	        					break;
	        			case 4: foodItems[i][j] = (Date)items.get(i).get(j); 
	        					break;
	        		}
	        	}
	        }
	        Object columnNames[] = {"Order ID", "Food Name", "Amount", "Total Calories", "Time"}; 
	        expensePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Daily Calories Consumption List:" ) );
	        JTable table = new JTable(foodItems, columnNames);
	        
	        JScrollPane scroll = new JScrollPane ( table );
	        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	        expensePanel.add(scroll, BorderLayout.CENTER);
        }
        expensePanel.setVisible(false);
        add(expensePanel);
        gc.gridwidth = 0; 
        layout.setConstraints(expensePanel, gc);
    }
    
    private boolean updateCaloriesAvailable() {
    	int caloriesPreference; 
    	if (!t_set_fund.getText().isEmpty()) {
    		caloriesPreference = Integer.parseInt(t_set_fund.getText()); 
    	}else {
    		caloriesPreference = DAOFactory.getUserDAO().findCaloriesPreferenceById(UserSession.getInstance().getCurrentUser().getId()); 
    	}
    	int caloriesConsumed_vm = DAOFactory.getUserDAO().getCalorieConsumed_vm(UserSession.getInstance().getCurrentUser());
        int caloriesConsumed_cafe = DAOFactory.getUserDAO().getCalorieConsumed_Cafe(UserSession.getInstance().getCurrentUser());
    	int calorieLeft = caloriesPreference - caloriesConsumed_cafe-caloriesConsumed_vm;
    	if (calorieLeft < 0) {
    		l_message.setText("New setting cannot be less than consumed amount");
    		l_message.setForeground(Color.RED);
    		return false; 
    	}else {
    		l_funds.setText("                " + String.valueOf(calorieLeft));
    		return true; 
    	}
    }
}
