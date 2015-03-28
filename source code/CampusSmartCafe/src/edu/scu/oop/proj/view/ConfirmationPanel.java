package edu.scu.oop.proj.view;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.event.*;

import edu.scu.oop.proj.dao.DAOFactory;
import edu.scu.oop.proj.entity.User;
import edu.scu.oop.proj.entity.UserSession;

//Confirmation Panel displays the confirmation page after purchase is complete. 
public class ConfirmationPanel extends JPanel {
	private static final String VENDING_MACHINE = "VendingMachine"; 
	private static final String CAFE = "Cafe"; 
	private JLabel titleLabel; 
	private JLabel pickupTimeLabel; 
	private JLabel orderNumberLabel; 
	private JLabel locationLabel; 
	private JLabel foodListLabel; 
	private JLabel messageLabel;
	private JButton backButton; 
	private MapPanel mapPanel; 
	private User currUser; 
	private ArrayList<String> currOrderNames; 
	
	public ConfirmationPanel() {
		super(); 
	}
	
	//intiialize the Confirmation page by different storeType - CAFE or VendingMachine 
	public ConfirmationPanel(String storeType){
		super(); 
		currUser = UserSession.getInstance().getCurrentUser();
		currOrderNames = new ArrayList<String>(); 
		if (currUser.getOrder() != null) {
			currOrderNames = currUser.getOrder().getOrderName(); 
		}
		GridBagLayout layout = new GridBagLayout(); 
		setLayout(layout); 
		GridBagConstraints gc = new GridBagConstraints(); 
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 0; 
		gc.fill = GridBagConstraints.BOTH;
		
		//If ordered via CAFE 
		if (storeType == CAFE) {
			titleLabel = new JLabel("Order Confirmation"); 
			Font font = titleLabel.getFont();
			titleLabel.setFont(new Font(font.getFontName(), Font.BOLD, 20));
			String pickup_address = DAOFactory.getOrderDAO().getOrderVenue(currUser);
			
			//display the corresponding information 
			pickupTimeLabel = new JLabel("Pick Up Time:          10 mins"); 
			orderNumberLabel = new JLabel("Order Number:         " + currUser.getOrder().getID()); 
			locationLabel = new JLabel("Location:                 " + pickup_address); 
			foodListLabel = new JLabel("Order Details: "); 
			backButton = new JButton("Back to Homepage"); 	 
			
			this.add(titleLabel); 
			this.add(pickupTimeLabel);
			this.add(orderNumberLabel); 
			this.add(locationLabel); 
			this.add(foodListLabel); 
			int count = 0; 
			
			//display the current order items' name and amount ordered 
			for (int i = 0; i < currOrderNames.size(); i++) {
				count = i + 1; 
				String currOrderName = currOrderNames.get(i); 
				int currOrderAmount = currUser.getOrder().getOrderItems().get(i).getFoodAmount(); 
				JLabel currItemLabel = new JLabel(count + "                             " + currOrderName + " " + currOrderAmount + " pieces"); 
				this.add(currItemLabel); 
				gc.gridwidth = 0; 
				gc.insets = new Insets(15,30, 15,30); 
				layout.setConstraints(currItemLabel, gc);
			}
			
			this.add(backButton); 
			
			//set the layout 
			gc.gridwidth = 0; 
			gc.insets = new Insets(30,30,30,30); 
			gc.anchor = GridBagConstraints.WEST;
			layout.setConstraints(titleLabel, gc);
			
			gc.gridwidth = 0; 
			gc.insets = new Insets(30,30,30,30); 
			layout.setConstraints(pickupTimeLabel, gc); 
			
			gc.gridwidth = 0; 
			layout.setConstraints(orderNumberLabel, gc); 
			
			gc.gridwidth = 0; 
		    layout.setConstraints(locationLabel, gc); 
			
			gc.gridwidth = 0; 
		 	layout.setConstraints(foodListLabel, gc);
			
			gc.gridwidth = 0; 
			layout.setConstraints(backButton, gc);

		}
		
		//if it's ordered via Vending Machine 
		else {
			titleLabel = new JLabel("            Order Confirmation"); 
			Font font = titleLabel.getFont();
			titleLabel.setFont(new Font(font.getFontName(), Font.BOLD, 20));
			messageLabel = new JLabel("Thanks for purchasing. Press button to go back to homepage");
			backButton = new JButton("Back to Homepage"); 
			
			this.add(titleLabel, gc); 
			this.add(messageLabel, gc); 
			this.add(backButton, gc); 
			
			gc.gridwidth = 0; 
			gc.insets = new Insets(30,30,30,30); 
			layout.setConstraints(messageLabel, gc);
			
			gc.gridwidth = 0; 
			gc.insets = new Insets(30,30,30,30); 
			layout.setConstraints(titleLabel, gc);
			
			gc.gridwidth = 0; 
			gc.insets = new Insets(30,30,30,30); 
			layout.setConstraints(backButton, gc);
			
		}
		backButton.addActionListener(new BackToHomepageActionListner());
	}
	
	class BackToHomepageActionListner implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ContentPanel contentPanel = (ContentPanel)SwingUtilities.getAncestorOfClass(ContentPanel.class, ConfirmationPanel.this);
			contentPanel.remove(ConfirmationPanel.this);
			MainFrame frame = (MainFrame)SwingUtilities.getRoot(contentPanel);  
			contentPanel.add(new BackgroundPanel()); 
			frame.validate();
			frame.repaint();
	        frame.setVisible(true);
		}
	}
	

}
