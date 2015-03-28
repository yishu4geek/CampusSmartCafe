package edu.scu.oop.proj.view;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import com.mysql.jdbc.PreparedStatement;

import edu.scu.oop.proj.dao.DAOFactory;
import edu.scu.oop.proj.entity.NoUserLoggedinException;
import edu.scu.oop.proj.entity.PieChart;
import edu.scu.oop.proj.entity.User;
import edu.scu.oop.proj.entity.UserSession;
import edu.scu.oop.proj.entity.UserSessionException;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


//HomebuttonPanel is the primary background panel for the left section of the GUI 
public class HomebuttonPanel extends JPanel{
    private Container pane;
    JPanel buttonPanel,loginPanel,fundPanel;
	static JPanel caloriePanel;
	JPanel east;
	JPanel picPanel;
	JPanel containerpanel;
	JPanel fund_Panel;
	JPanel calorie_Panel;
	JPanel map_panel;
	JPanel dietary_panel; 
	JPanel fp;
	JPanel loggedinPanel;
	static JPanel piePanel; 
    JPanel loginContainerPanel, pieLabel_Panel; 
    JLabel l_name, l_password, l_CSCnumber,welcomeLabel,message,l_fundpie,l_fundpie1; 
    JLabel logged_name, logged_account, logged_username, logged_gender, l_caloriepie, l_caloriepie1, l_caloriepie2; 
    JTextField name,CSCnumber;
    JPasswordField password; 
    JButton viewFunds, viewCalories, viewMap, viewStats, ok, logout;
    BufferedImage welcome;
    JPanel containerPanel;
    UserSession session; 
    double carbs_comsume = 200, proteins_comsume = 300, fats_comsume = 500; 
    ArrayList<Integer> pieData = new ArrayList<Integer>();
    static JLabel l_caloriepieCarbs;
    static JLabel l_caloriepieFats;
    static ArrayList<Color> colors = new ArrayList<Color>();
    static PieChart pc;
    private static JLabel l_caloriepieProteins;
    private static GridBagConstraints gc;


    public HomebuttonPanel() {
        super();
        setLayout(new GridLayout(0,1));
        setPreferredSize(new Dimension(200,700));
        Border outline = BorderFactory.createLineBorder(Color.black);
        setVisible(true);
        JPanel fp = GUIManager.getInstance().getContent_panel();

        //*********************************create button panel**********************************
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        buttonPanel.setBackground(Color.lightGray);
        
        //Order Food button 
        viewMap = new JButton();
        Font font = viewMap.getFont();
        viewMap.setText("Order Food");
        viewMap.setActionCommand("Order Food");
        viewMap.setFont(new Font(font.getFontName(), Font.BOLD, 13));
        viewMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JPanel fp = GUIManager.getInstance().getContent_panel();
                MainFrame frame = (MainFrame)SwingUtilities.getRoot(HomebuttonPanel.this); 
                fp.removeAll();
                map_panel = new MapPanel();
                fp.add(new BackgroundPanel());
                frame.validate();
                frame.repaint(); 
            }
        });
        
        //Calories Manager button 
        viewCalories = new JButton();
        viewCalories.setText("Calories Manager");
        viewCalories.setFont(new Font(font.getFontName(), Font.BOLD, 13));
        viewCalories.setActionCommand("Calories Manager");
        viewCalories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JPanel fp = GUIManager.getInstance().getContent_panel();
                fp.removeAll();
                calorie_Panel = new ViewCaloriePanel();
                fp.add(calorie_Panel);
                fp.validate();
                fp.repaint();
                fp.setVisible(true);
            }
        });
        
        //Fund Manager button 
        viewFunds = new JButton();
        viewFunds.setText("Fund Manager");
        viewFunds.setFont(new Font(font.getFontName(), Font.BOLD, 13));
        viewFunds.setActionCommand("Fund Manager");
        viewFunds.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                JPanel fp = GUIManager.getInstance().getContent_panel();
                fp.removeAll();
                fund_Panel = new ViewFundPanel();
                fp.add(fund_Panel);
                fp.validate();
                fp.repaint();
                fp.setVisible(true);
            }
        });
        
        //Dietary Statitstics button 
        viewStats = new JButton(); 
        viewStats.setText("Dietary Statistics");
        viewStats.setFont(new Font(font.getFontName(), Font.BOLD, 13));
        viewStats.setActionCommand("Dietary Statistics");
        viewStats.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                JPanel fp = GUIManager.getInstance().getContent_panel();
                fp.removeAll();
                dietary_panel = new DietaryPanel();
                fp.add(dietary_panel);
                fp.validate();
                fp.repaint();
                fp.setVisible(true);
            }
        });
        
        
        buttonPanel.add(viewMap);
        buttonPanel.add(viewCalories);
        buttonPanel.add(viewFunds);
        buttonPanel.add(viewStats);

        viewMap.setEnabled(false);
        viewCalories.setEnabled(false);
        viewFunds.setEnabled(false);
        viewStats.setEnabled(false);
        
        add(buttonPanel);

        //********************create pieLabel_Panel****************************************************
        piePanel = new JPanel(); 

        pieLabel_Panel  = new JPanel();
        GridBagLayout layout = new GridBagLayout(); 
        piePanel.setLayout(layout);


        double total = carbs_comsume + proteins_comsume + fats_comsume;
        double carbsPercent = (carbs_comsume/total)*100;
        double proteinPercent = (proteins_comsume/total)*100;
        double fatsPercent =  (fats_comsume/total)*100;

        l_caloriepieCarbs = new JLabel("Carbs Consumed: " + carbsPercent + "%");
        l_caloriepieCarbs.setForeground(new Color(255, 104, 228));

        l_caloriepieProteins = new JLabel("Proteins Consumed: " + proteinPercent + "%");
        l_caloriepieProteins.setForeground(new Color(98, 186, 121));

        l_caloriepieFats = new JLabel("Fats Consumed: " + fatsPercent + "%");
        l_caloriepieFats.setForeground(new Color(167, 96, 255));

        GridBagConstraints gc = new GridBagConstraints(); 
        gc.gridwidth = 0; 
        piePanel.add(l_caloriepieCarbs, gc);
        piePanel.add(l_caloriepieProteins, gc); 
        piePanel.add(l_caloriepieFats, gc); 


        //********************create caloriePanel**************************************************
        caloriePanel = new JPanel();
        ArrayList<Double> values = new ArrayList<Double>();

        values.add(new Double(carbsPercent));
        values.add(new Double(proteinPercent));
        values.add(new Double(fatsPercent));

        colors.add(new Color(255, 104, 228));
        colors.add(new Color(98, 186, 121));
        colors.add(new Color(167, 96, 255));
        pc = new PieChart(values, colors);

        caloriePanel.add(pc); 
        piePanel.add(caloriePanel,gc); 
        add(piePanel); 

        
        //********************************create login container panel**************************
        loginContainerPanel = new JPanel(); 
        loginContainerPanel.setLayout(new BorderLayout());
        
        //********************************create log in panel****************************************
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(8,1));
        loginPanel.setBackground(Color.lightGray);
        l_password = new JLabel("  Password:");
        password = new JPasswordField();
        password.setEchoChar('*');
        l_CSCnumber = new JLabel("  CSC ID:");
        CSCnumber = new JTextField();
        message = new JLabel();
        ok = new JButton();
        ok.setText("Login");
        
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String s_number = CSCnumber.getText();
                char[] c_password = password.getPassword();
                String s_password = new String(c_password);
                session = UserSession.getInstance(); 
                if (session == null) {
                	System.out.println("NULL!!!!"); 
                }
                
                //if all entries have been filled 
                if(s_number!=null&&!s_number.isEmpty()&&s_password!=null&&!s_password.isEmpty()){
            		try {
            			boolean validLogin = session.login(s_number, s_password); 
            			if (validLogin) {
            				//userID and password is valid 
            				HomebuttonPanel.this.login(); 
            				User currUser = UserSession.getInstance().getCurrentUser(); 
            			}
	            		else {
	            			//invalid id/password 
	            			System.out.println("no record");
	            			message.setText("User/Password doesn't match");
	            		}
            		}catch (UserSessionException e) {
            			message.setText(e.toString()); 
            		} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
            		
            	//some of the fields are empty 
                }else{
                    if(s_number==null||s_number.isEmpty()){
                        message.setText("Don't forget the CSCnumber!");
                    }
                    if(s_password==null|| s_password.isEmpty()){
                        message.setText("Don't forget the password!");
                    }
                }
            }
        });
        
        loginPanel.add(l_CSCnumber);
        loginPanel.add(CSCnumber);
        loginPanel.add(l_password);
        loginPanel.add(password);
        loginPanel.add(message);
        loginPanel.add(ok);
        loginContainerPanel.add(loginPanel, BorderLayout.CENTER);
        add(loginContainerPanel);
    }
    
    private void login() {
    	HomebuttonPanel homebuttonPanel = (HomebuttonPanel)SwingUtilities.getAncestorOfClass(HomebuttonPanel.class, loginPanel);
		loginContainerPanel.remove(loginPanel);		
		MainFrame frame = (MainFrame)SwingUtilities.getRoot(homebuttonPanel); 
		
		//********************create loggedinPanel******************
		loggedinPanel = new JPanel(); 
        loggedinPanel.setLayout(new GridLayout(8,1));
        loggedinPanel.setBackground(Color.lightGray);
        User currUser = UserSession.getInstance().getCurrentUser(); 
        logged_name = new JLabel("Welcome: " + currUser.getName()); 
        logged_gender = new JLabel("Gender: " + currUser.getGender()); 
        logout = new JButton("Log out"); 
        logged_name.setHorizontalAlignment(JLabel.CENTER);
        logged_gender.setHorizontalAlignment(JLabel.CENTER);
        
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	HomebuttonPanel.this.logout();
            }
        });
        
        GUIManager.getInstance().content_panel.removeAll();
        GUIManager.getInstance().content_panel.add(new BackgroundPanel()); 
       
        pieData = DAOFactory.getUserDAO().getPieData(currUser);

       carbs_comsume = pieData.get(0);
       fats_comsume = pieData.get(1);
       proteins_comsume = pieData.get(2);
       
       //update the piechart based on user profile 
       updatPieChartLabel(carbs_comsume,fats_comsume,proteins_comsume);
       frame.validate();
       frame.repaint();
      
        loggedinPanel.add(new JLabel());
        loggedinPanel.add(logged_name); 
        loggedinPanel.add(new JLabel());
        loggedinPanel.add(logged_gender); 
        loggedinPanel.add(new JLabel());
        loggedinPanel.add(logout); 
        loggedinPanel.add(new JLabel());
		loginContainerPanel.add(loggedinPanel); 
		frame.validate();
		frame.repaint();
        
		viewMap.setEnabled(true);
         viewCalories.setEnabled(true);
         viewFunds.setEnabled(true);
         viewStats.setEnabled(true);
    }
    
    private void logout() {
    	try {
    		UserSession.getInstance().logout();
    		HomebuttonPanel homebuttonPanel = (HomebuttonPanel)SwingUtilities.getAncestorOfClass(HomebuttonPanel.class, loggedinPanel);
    		loginContainerPanel.remove(loggedinPanel);		
    		MainFrame frame = (MainFrame)SwingUtilities.getRoot(homebuttonPanel); 
    		loginContainerPanel.add(loginPanel); 
    		message.setText("");
    		CSCnumber.setText("");
    		password.setText("");
    		
    		viewMap.setEnabled(false);
            viewCalories.setEnabled(false);
            viewFunds.setEnabled(false);
            viewStats.setEnabled(false);
            
            GUIManager.getInstance().content_panel.removeAll(); 
            GUIManager.getInstance().content_panel.add(new BackgroundPanel()); 
            
            carbs_comsume = 200;
            proteins_comsume = 300;
            fats_comsume = 500;
            updatPieChartLabel(carbs_comsume,fats_comsume,proteins_comsume);
            
    		frame.validate();
    		frame.repaint();
            frame.setVisible(true);
    	}catch (NoUserLoggedinException e) {
    		e.getMessage(); 
    	}
    }
    
   

    public static void updatPieChartLabel(double carbs_comsume, double fats_comsume, double proteins_comsume){

        double total = carbs_comsume + proteins_comsume + fats_comsume;
        System.out.println("Total consume: " +total);
        int i_carbsPercent = (int)((carbs_comsume/total)*100);
        int i_proteinPercent = (int)((proteins_comsume/total)*100);
        int i_fatsPercent =  100 - i_carbsPercent - i_proteinPercent;
        double carbsPercents = (double)i_carbsPercent;
        double proteinPercents = (double)i_proteinPercent;
        double fatsPercents =(double)i_fatsPercent;
        l_caloriepieCarbs.setText("Carbs Consumed: " + carbsPercents + "%");
        l_caloriepieProteins.setText("Proteins Consumed: " + proteinPercents + "%");
        l_caloriepieFats.setText("Fats Consumed: " + fatsPercents + "%");

        caloriePanel.remove(pc);
        piePanel.remove(caloriePanel);

        ArrayList<Double> value = new ArrayList<Double>();

        value.add(new Double(carbsPercents));
        value.add(new Double(proteinPercents));
        value.add(new Double(fatsPercents));
        for(int i =0;i<value.size();i++){
           System.out.println(value.get(i));
      }
        System.out.println("Value equals: " + value);
        pc = new PieChart(value, colors);
                caloriePanel.add(pc);
        piePanel.add(caloriePanel, gc); 
    }
}




