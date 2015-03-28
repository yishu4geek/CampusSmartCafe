package edu.scu.oop.proj.view;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*; 

import edu.scu.oop.proj.dao.DAOFactory;
import edu.scu.oop.proj.entity.Food;
import edu.scu.oop.proj.entity.Order;
import edu.scu.oop.proj.entity.OrderItem;
import edu.scu.oop.proj.entity.User;
import edu.scu.oop.proj.entity.UserSession;
import edu.scu.oop.proj.validator.PreferenceValidator;


//OrderPanel is the background panel for food menu page - which holds the Title panel, MenuPanel and submit panel 
public class OrderPanel extends JPanel{
	private static final String VENDING_MACHINE = "VendingMachine"; 
	private static final String CAFE = "Cafe"; 
	private JPanel titlePanel; 
	private MenuPanel menuPanel; 
	private MapPanel mapPanel; 
	private ConfirmationPanel confirmationPanel; 
	private JPanel submitPanel; 
	private JLabel titleLabel; 
	private JButton submitButton; 
	private JButton backButton; 
	private JLabel messageLabel; 
	private String storeType; 
	private PreferenceValidator pv;
	double carbs_comsume;
    double proteins_comsume;
    double fats_comsume;
    ArrayList<Integer> pieData = new ArrayList<Integer>();
	
	public OrderPanel() {
		super(); 
	}
	
	public OrderPanel(String storeType) {
		super(); 
		titlePanel = new JPanel(); 
		menuPanel = new MenuPanel(storeType); 
		submitPanel = new JPanel(); 
		this.storeType = storeType; 
		
		//set titlePanel component
		if (storeType == CAFE) {
			titleLabel = new JLabel("Cafe Menu");
		}
		else {
			titleLabel = new JLabel("Vending Machine Menu"); 
		}
		Font font = titleLabel.getFont();
		titleLabel.setFont(new Font(font.getFontName(), Font.BOLD, 20));
		titlePanel.add(titleLabel); 
		
		//set submitPanel component
		submitPanel.setLayout(new BoxLayout(submitPanel, BoxLayout.Y_AXIS));
		submitButton = new JButton("Submit"); 
		backButton = new JButton("Back to homepage"); 
		messageLabel = new JLabel(""); 
		submitPanel.add(submitButton);
		submitPanel.add(backButton);
		submitPanel.add(messageLabel); 
		submitButton.addActionListener(new SubmitOrderActionListener());
		backButton.addActionListener(new BackToHomepageActionListner());
		submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		add(titlePanel); 
		add(menuPanel); 
		GUIManager.getInstance().setMenu_Panel(menuPanel);
		add(submitPanel); 
		
		//set layout for orderPanel 
		GridLayout layout1 = new GridLayout(0, 1); 
		GridBagLayout layout = new GridBagLayout(); 
		setLayout(layout);
		GridBagConstraints gc = new GridBagConstraints(); 
		
		gc.gridwidth = 0; 
		gc.insets = new Insets(10, 10, 15, 10); 
		layout.setConstraints(titlePanel, gc);
		
		gc.gridwidth = 0; 
		gc.ipady = 40; 
		layout.setConstraints(menuPanel, gc);
		
		gc.gridwidth = 0; 
		layout.setConstraints(submitPanel, gc);
		 
		GUIManager.getInstance().setMenu_Panel(menuPanel);
	}
	
	class SubmitOrderActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			User currUser = UserSession.getInstance().getCurrentUser(); 
			ContentPanel contentPanel = (ContentPanel)SwingUtilities.getAncestorOfClass(ContentPanel.class, OrderPanel.this);
			MainFrame frame = (MainFrame)SwingUtilities.getRoot(contentPanel); 
			
			pv = new PreferenceValidator(); 
			int count = menuPanel.getSelection().size();
			ArrayList<OrderItem> foodList = new ArrayList<OrderItem>(); 
			int numOfCafeFood = DAOFactory.getFoodDAO().findNumOfItems(CAFE); 
			
			//iterate through the food list to see which food is selected and add them into OrderItem 
			for (int i = 0; i < count; i++) {
				JCheckBox currCheckBox = menuPanel.getSelection().get(i);
				if (currCheckBox.isSelected()) {
					if (storeType == CAFE) {
						JComboBox currComboBox = menuPanel.getAmounts().get(i); 
						int currAmount = (Integer)currComboBox.getSelectedItem(); 
						foodList.add(new OrderItem(i + 1, currAmount)); 
					}else {
						foodList.add(new OrderItem(i + numOfCafeFood + 1, 0));
					}
				}
			}
			//if no food is selected, display error msg 
			if (foodList.isEmpty()) {
				messageLabel.setText("Please select food first");
				messageLabel.setForeground(Color.RED);
			} else {
				//calculate the order's bill 
				float totalFund = pv.calculateBill(foodList, storeType); 
				
				//calculate the order's total calories 
				int totalCalories = pv.calculateCalories(foodList, storeType); 
				
				//validate if it meets fund preference 
				boolean fundValid = pv.validateFundPreference(currUser, foodList, storeType); 
				
				//validate if it meets calories preference 
				boolean caloriesValid = pv.validateCaloriesPreference(currUser, foodList, storeType);
				
				//if violates both criterias 
				if (!fundValid && !caloriesValid) {
					JOptionPane.showMessageDialog(frame, "Fund and Calories quota insufficient. Set preference on the left bar");
				//if violates fund criteria
				}else if (!fundValid) {
					JOptionPane.showMessageDialog(frame, "Fund insufficient. Set preference via 'Fund Manager'");
				//if violates calories criteria 
				}else if (!caloriesValid) {
					JOptionPane.showMessageDialog(frame, "Calories quota insufficient. Set preference via 'Calories Manager'");
				//valid order, proceed to create the order and goes to the confirmation page 
				}else {
					Order order = UserSession.getInstance().getCurrentUser().createOrder();  
					order.setType(storeType);
					order.setLocation(GUIManager.getInstance().getMap_panel().getSelectedButtonText()); 	
					
					for (int i = 0; i < count; i++) {
						JCheckBox currCheckBox = menuPanel.getSelection().get(i); 
						if(currCheckBox.isSelected()) {
							if (storeType == CAFE) {
								JComboBox currComboBox = menuPanel.getAmounts().get(i); 
								int currAmount = (Integer)currComboBox.getSelectedItem(); 
								order.addOrderItem(new OrderItem(i + 1, currAmount));
							}else {
								order.addOrderItem(new OrderItem(i + numOfCafeFood + 1, 0));
							}
						}
					}
					
					//create order and add the order items into database 
					DAOFactory.getOrderDAO().create(UserSession.getInstance().getCurrentUser());
					
					//update the calories consumed for the user in the database 
					DAOFactory.getUserDAO().updateCaloriesConsumedById(currUser.getId(), totalCalories);
					
					//update the fund spent value for the user in the database 
					DAOFactory.getUserDAO().updateFundSpentById(currUser.getId(), totalFund);
					
					//go to confirmation page 
					contentPanel.remove(OrderPanel.this);		
					confirmationPanel = new ConfirmationPanel(storeType);
					contentPanel.add(confirmationPanel); 
					frame.validate();
			        frame.setVisible(true);
			        pieData = DAOFactory.getUserDAO().getPieData(UserSession.getInstance().getCurrentUser());
		            carbs_comsume = pieData.get(0);
		            fats_comsume = pieData.get(1);
		            proteins_comsume = pieData.get(2);
		            HomebuttonPanel.updatPieChartLabel(carbs_comsume,fats_comsume,proteins_comsume);
		            frame.validate();
		            frame.repaint();
				}
			}
		}
	}
	
	class BackToHomepageActionListner implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ContentPanel contentPanel = (ContentPanel)SwingUtilities.getAncestorOfClass(ContentPanel.class, OrderPanel.this);
			contentPanel.remove(OrderPanel.this);
			MainFrame frame = (MainFrame)SwingUtilities.getRoot(contentPanel); 
			mapPanel = new MapPanel(); 
			contentPanel.add(new BackgroundPanel()); 
			frame.validate();
			frame.repaint();
	        frame.setVisible(true);
		}
	}
}
