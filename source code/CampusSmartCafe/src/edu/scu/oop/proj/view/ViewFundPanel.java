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


public class ViewFundPanel extends JPanel {

    JLabel l_available_funds, l_set_fund,l_funds, l_existing_fund, l_spent, l_spent_amount, l_spent_message; 
    JButton b_edit, b_view_detailed_exp,b_back, b_save; 
    JTextArea t_expense_list;
    JTextArea t_set_fund; 
    JPanel settingfundPanel,buttonPanel,expensePanel,map_panel;
    JPanel settingPanel;
    JPanel l_preference, l_edit; 
    int expense = 100;
    Font labelFont = new Font("Serif", Font.PLAIN, 30);
    Font labelFont_black = new Font("BLACK", Font.PLAIN, 15);
    JLabel l_message; 
    float fun_spent; 
    float fund_preference; 
    GridBagLayout layout; 


    public ViewFundPanel(){
        super();
       layout = new GridBagLayout(); 
        setLayout(layout); 
        GridBagConstraints gc = new GridBagConstraints(); 
        setPreferredSize(new Dimension(1000, 1200));
        setVisible(true);
        
        //**********************create setting fund panel**************************************************
        settingfundPanel = new JPanel(new GridLayout(0,3));
        l_preference = new JPanel(); 
        l_edit = new JPanel(); 
       
        fund_preference = DAOFactory.getUserDAO().findFundPreferenceById(UserSession.getInstance().getCurrentUser().getId()); 
        l_existing_fund = new JLabel("$ " + String.valueOf(fund_preference)); 
        t_set_fund = new JTextArea(2,15); 
        Border border = BorderFactory.createLineBorder(new Color(128, 128, 128));
        t_set_fund.setBorder(BorderFactory.createCompoundBorder(border, 
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        l_funds = new JLabel();
        l_set_fund = new JLabel("Fund Preference/month: ");
        Font font = l_set_fund.getFont();
        l_set_fund.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() ));
        l_available_funds = new JLabel("Available Fund: ");
        font = l_available_funds.getFont();
        l_available_funds.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() ));
        b_edit = new JButton("EDIT");
        b_save = new JButton("SAVE"); 
        l_message = new JLabel(); 
        b_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	MainFrame frame = (MainFrame)SwingUtilities.getRoot(ViewFundPanel.this); 
        		float preference_setting = Float.parseFloat(t_set_fund.getText()); 
        		if (preference_setting < 0) {
        			l_message.setText("ERROR : fund cannot be negative number");
        		}else if (t_set_fund.getText() == null) {
        			l_message.setText("ERROR: please enter a number");
        		}else {
        			boolean updateFundAvailable = ViewFundPanel.this.updateFundAvailable(); 
        			if (updateFundAvailable) {
	        			DAOFactory.getUserDAO().setFundPreferenceById(UserSession.getInstance().getCurrentUser().getId(), preference_setting);
	        			l_preference.removeAll();
	        			l_preference.add(l_existing_fund); 
	        			l_existing_fund.setText("$ " + String.valueOf(preference_setting));
	        			l_edit.removeAll();
	                	l_edit.add(b_edit);
	                	l_message.setText("");
	                	frame.validate();
	    		        frame.setVisible(true);        
        			}
        		}
            }
        });
        
        b_edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            		
            	MainFrame frame = (MainFrame)SwingUtilities.getRoot(ViewFundPanel.this); 
            	l_preference.removeAll();
            	l_preference.add(t_set_fund); 
            	l_edit.removeAll();
            	l_edit.add(b_save); 
            	frame.validate();
		        frame.setVisible(true);
            }
        });

        settingfundPanel.add(l_set_fund);
        settingfundPanel.add(l_preference);
        l_preference.add(l_existing_fund); 
        settingfundPanel.add(l_edit);
        l_edit.add(b_edit); 
        settingfundPanel.add(l_available_funds);
        this.updateFundAvailable(); 
        settingfundPanel.add(l_funds);
        settingfundPanel.add(l_message);
        add(settingfundPanel); 
        gc.gridwidth = 0; 
        layout.setConstraints(settingfundPanel, gc);

        //**************************create button panel*******************************************************
        buttonPanel = new JPanel();
        b_view_detailed_exp = new JButton("See Expense Detail");
        b_view_detailed_exp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	GridBagConstraints gc = new GridBagConstraints(); 
            	gc.gridwidth = 0;
                gc.ipadx = 100; 
                gc.gridheight = 3; 
                layout.setConstraints(expensePanel, gc);
                expensePanel.setVisible(true);
            }
        });
        b_back = new JButton("BACK");
        b_back.setActionCommand("BACK");
        b_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	MainFrame frame = (MainFrame)SwingUtilities.getRoot(ViewFundPanel.this); 
                JPanel fp = GUIManager.getInstance().getContent_panel();
                fp.removeAll();
                map_panel = new MapPanel();
                fp.add(new BackgroundPanel());
                frame.validate();
                frame.repaint();
                fp.setVisible(true);
            }
        });
        buttonPanel.add(b_view_detailed_exp);
        buttonPanel.add(b_back);
        add(buttonPanel); 
        gc.gridwidth = 0; 
        layout.setConstraints(buttonPanel, gc);
        //****************************Expense List Panel********************************************************

        expensePanel  = new JPanel ();
        expensePanel.setLayout(new BorderLayout());
        expensePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Expense List:" ) );
        ArrayList<ArrayList<Object> > items = DAOFactory.getUserDAO().getFundHistory(UserSession.getInstance().getCurrentUser().getId());
        if (items.size() == 0) {
        	JLabel noTranLabel = new JLabel("No purchase history yet"); 
        	expensePanel.add(noTranLabel, BorderLayout.CENTER); 
        	
        }
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
	        			case 3: foodItems[i][j] = (Float)items.get(i).get(j); 
	        					if (amount > 1) {
	        						foodItems[i][j] = (Float)foodItems[i][j] * amount; 
	        					}
	        					break;
	        			case 4: foodItems[i][j] = (Date)items.get(i).get(j); 
	        					break;
	        		}
	        	}
	        }
	        Object columnNames[] = {"Order ID", "Food Name", "Amount", "Total Price", "Time"}; 
	        expensePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Monthly Expense List:" ) );
	        JTable table = new JTable(foodItems, columnNames);
	        
	        JScrollPane scroll = new JScrollPane ( table );
	        scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	        expensePanel.add(scroll, BorderLayout.CENTER);
        }

        expensePanel.setVisible(false);
        add(expensePanel); 
        gc.gridwidth = 0;
        gc.ipadx = 100; 
        layout.setConstraints(expensePanel, gc);
    }
    
    
    private boolean updateFundAvailable() {
    	float fundPreference; 
    	if (!t_set_fund.getText().isEmpty()) {
    		fundPreference = Float.parseFloat(t_set_fund.getText()); 
    	}else {
    		fundPreference = DAOFactory.getUserDAO().findFundPreferenceById(UserSession.getInstance().getCurrentUser().getId()); 
    	}
    	float fundSpentVM = DAOFactory.getUserDAO().getFundSpendVM(UserSession.getInstance().getCurrentUser());
        float fundSpentCafe = DAOFactory.getUserDAO().getFundSpendCafe(UserSession.getInstance().getCurrentUser());
    	float fundLeft = fundPreference - fundSpentVM - fundSpentCafe;
    	if (fundLeft < 0) {
    		l_message.setText("New setting cannot be less than spent amount");
    		l_message.setForeground(Color.RED);
    		return false; 
    	}else {
    		l_funds.setText("              $ " + String.valueOf(fundLeft));
    		return true; 
    	}
    }

}
