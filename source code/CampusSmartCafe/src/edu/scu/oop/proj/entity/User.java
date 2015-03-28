package edu.scu.oop.proj.entity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.mysql.jdbc.PreparedStatement;

import edu.scu.oop.proj.dao.DAOFactory;
import edu.scu.oop.proj.dao.MySQLAccess;

public class User {
	private String username; 
	private int id; 
	private String password; 
	private String name; 
	private Gender gender; 
	private Order order; 
	private int calorieConsumed; 
	private int fundSpend; 
	private Preference preference; 
	private boolean fundUpdated = false;
	private boolean caloriesUpdated = false; 
	
	public User() { 
		
	}
	
	public User(String username, String password, String name, Gender gender, int calorieConsumed, int fundSpend) {
		this.username = username; 
		this.password = password; 
		this.name = name; 
		this.gender = gender;
		this.calorieConsumed = 0; 
		this.fundSpend = 0; 
		this.preference = new Preference(); 
	}
	
	
	public void setId(int id) {
		this.id = id; 
	}
	
	public String getName() {
		return this.name; 
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username; 
	}
	
	public Gender getGender() {
		return this.gender; 
	}
	
	public void setGender(Gender gender) {
		this.gender = gender; 
	}
	
	public void setPassword(String password) {
		this.password = password; 
	}
	
	public String getPassword() {
		return this.password; 
	}
	
	public void setCalorieConsumed(int calorieConsumed) {
		this.calorieConsumed = calorieConsumed;
	}
	
	public void setFundSpend(int fundSpend) {
		this.fundSpend = fundSpend; 
	}

 	
	public Order createOrder() {
		if (order != null) {
			order = null; 
		}
		order = new Order(); 
		return order; 
	}
	
	public Order getOrder() {
		return this.order; 
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setPreference(float fund, int calories) {
		this.preference.setCaloriesPreference(calories);
		this.preference.setFundPreference(fund);
	}
	
	public void setFundPreference(float fund) {
		this.preference.setFundPreference(fund);
	}
	
	public void setCaloriesPreference(int calories) {
		this.preference.setCaloriesPreference(calories);
	}
	
	public float getFundPreference() {
		return this.preference.getFundPreference(); 
	}
	
	public int getCaloriesPreference() {
		return this.preference.getCaloriesPreference(); 
	}
	
	public void refreshFundPreference() {
		if (!fundUpdated) {
			
		}
	}
 }
