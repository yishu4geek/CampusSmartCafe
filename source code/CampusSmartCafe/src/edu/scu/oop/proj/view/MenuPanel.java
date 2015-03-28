package edu.scu.oop.proj.view;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*; 

import com.mysql.jdbc.PreparedStatement;

import edu.scu.oop.proj.dao.DAOFactory;
import edu.scu.oop.proj.dao.MySQLAccess;
import edu.scu.oop.proj.entity.Food;

//MenuPanel display the food menus for CAFE or VendingMachine
public class MenuPanel extends JPanel{
	private static final String VENDING_MACHINE = "VendingMachine"; 
	private static final String CAFE = "Cafe"; 
	
	private Integer[] amount = {1, 2, 3, 4, 5}; 
	private ArrayList<JCheckBox> selection; 
	private ArrayList<JComboBox> amounts; 
	
	private String foodName; 
	private String foodPrice;
	private String foodCalorie;
	private String foodAmount; 
	private String foodCarbs;
	private String foodProtein;
	private String foodFat;
	private Food currFood; 
	
	
	public MenuPanel() {
		super(); 
		GridLayout layout = new GridLayout(); 
		setLayout(new GridLayout()); 
	}
	
	public MenuPanel(String storeType) {
		super(); 
		selection = new ArrayList<JCheckBox>(); 
		amounts = new ArrayList<JComboBox>(); 
		
		//retrieve the available food lists from database 
		ArrayList<Food> foodLists = DAOFactory.getFoodDAO().findElementsByType(storeType); 

		if (storeType == CAFE) {
			GridLayout layout = new GridLayout(0, 6); 
			setLayout(layout); 
			layout.setVgap(10); 
			this.add(new JLabel("Selected"));
			this.add(new JLabel("Name")); 
			this.add(new JLabel("Price")); 
			this.add(new JLabel("Calories")); 
			JLabel amountLabel = new JLabel("Amount"); 
			this.add(amountLabel);
			amountLabel.setHorizontalAlignment(JLabel.CENTER);
			this.add(new JLabel("Fat / Protein / Carbs")); 
			
			//display the food and its relevant information 
			for (int i = 0; i < foodLists.size(); i++) {
				currFood = foodLists.get(i); 
				foodName = currFood.getFoodName();  
				foodPrice = String.valueOf(currFood.getPrice()); 
				foodCalorie = String.valueOf(currFood.getCalories()); 
				foodFat = String.valueOf(currFood.getFat()); 
				foodProtein = String.valueOf(currFood.getProtein()); 
				foodCarbs = String.valueOf(currFood.getCarbs()); 
				
				JCheckBox selected = new JCheckBox(); 
				JComboBox foodAmount = new JComboBox(amount); 
				this.add(selected);
				selection.add(selected);
				
				this.add(new JLabel(foodName)); 
				this.add(new JLabel(foodPrice)); 
				this.add(new JLabel(foodCalorie)); 
				this.add(foodAmount); 
				amounts.add(foodAmount); 
				JLabel dietaryLabel = new JLabel(foodFat + " / " + foodProtein + " / " + foodCarbs); 
				this.add(dietaryLabel);
				dietaryLabel.setHorizontalAlignment(JLabel.CENTER);
			}
		}
		else if (storeType == VENDING_MACHINE) {
			GridLayout layout = new GridLayout(0, 5); 
			setLayout(layout); 
			layout.setVgap(10);
			this.add(new JLabel("Selected"));
			this.add(new JLabel("Name")); 
			this.add(new JLabel("Price")); 
			this.add(new JLabel("Calories")); 
			this.add(new JLabel("Fat / Protein / Carbs")); 
			for (int i = 0; i < foodLists.size(); i++) {
				currFood = foodLists.get(i);
				foodName = currFood.getFoodName();  
				foodPrice = String.valueOf(currFood.getPrice()); 
				foodCalorie = String.valueOf(currFood.getCalories()); 
				foodFat = String.valueOf(currFood.getFat()); 
				foodProtein = String.valueOf(currFood.getProtein()); 
				foodCarbs = String.valueOf(currFood.getCarbs()); 
				
				JCheckBox selected = new JCheckBox(); 
				this.add(selected);
				selection.add(selected); 
				this.add(new JLabel(foodName)); 
				this.add(new JLabel(foodPrice)); 
				this.add(new JLabel(foodCalorie)); 
				this.add(new JLabel(foodFat + " / " + foodProtein + " / " + foodCarbs)); 
			}
		}
	}
	
	public ArrayList<JCheckBox> getSelection() {
		return this.selection; 
	}
	
	public ArrayList<JComboBox> getAmounts() {
		return this.amounts; 
	}
 	
	class menuSelectItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			
		}
	}
}


