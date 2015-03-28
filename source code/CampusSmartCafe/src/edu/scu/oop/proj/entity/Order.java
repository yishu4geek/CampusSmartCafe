package edu.scu.oop.proj.entity;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import com.mysql.jdbc.PreparedStatement;

import edu.scu.oop.proj.dao.DAOFactory;
import edu.scu.oop.proj.dao.MySQLAccess;


public class Order {
	
	private int id; 
	private ArrayList<OrderItem> orderItems; 
	private ArrayList<Food> foodList; 
	private Date orderTime; 
	private String type; 
	private String location; 
	
	public Order() {
		Random randomGenerator = new Random(); 
		int order_id = randomGenerator.nextInt(100000); 
		System.out.println("order_id" + order_id); 
		this.id = order_id; 
		orderItems = new ArrayList<OrderItem>(); 
	}
	
	public int getID() {
		return this.id; 
	}
	
	
	public void addOrderItem(OrderItem item) {
		this.orderItems.add(item);
	}
	
	public ArrayList<OrderItem> getOrderItems() {
		return this.orderItems; 
	}
	
	public void setType(String type) {
		this.type = type; 
	}
	
	public String getType() {
		return this.type; 
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getLocation() {
		return this.location; 
	}
	
	//get order names based on the orderItem list 
	public ArrayList<String> getOrderName() {
		int currFoodId; 
		String currFoodName = ""; 
		ArrayList<String> foodNames = new ArrayList<String>(); 
		if (this.foodList == null) {
			this.foodList = DAOFactory.getOrderDAO().findElements(this); 
		}
		for (int i = 0; i < foodList.size(); i++) {
			currFoodName = foodList.get(i).getFoodName(); 
			foodNames.add(currFoodName); 
		}
		return foodNames; 
	} 
	
	//calculate the total calories of an order 
	public int calculateTotalCalorie() {
		ArrayList<Integer> foodCalories = new ArrayList<Integer>(); 
		if (this.foodList == null) {
			this.foodList = DAOFactory.getOrderDAO().findElements(this); 
		}
		int totalCalories = 0; 
		int currItemCalorie = 0; 
		for (int i = 0; i < foodList.size(); i++) {
			currItemCalorie = foodList.get(i).getCalories(); 
			totalCalories += currItemCalorie; 
		}
		return totalCalories; 
	}
 }
