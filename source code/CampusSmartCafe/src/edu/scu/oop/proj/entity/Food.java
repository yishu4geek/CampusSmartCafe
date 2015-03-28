package edu.scu.oop.proj.entity;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import edu.scu.oop.proj.dao.MySQLAccess;


public class Food {
	private int id; 
	private String name; 
	private int amount; 
	private String type; 
	private float price;
	private int calorie;
	private int fat;
	private int carbs;
	private int protein; 
	
	public Food(int id) {
		
	}
	
	public Food(int id, String name, int amount, String type, float price, int calorie, int fat, int carbs, int protein) {
		this.id = id; 
		this.name = name; 
		this.amount = amount; 
		this.type = type;
		this.price = price;
		this.calorie = calorie;
		this.fat = fat; 
		this.carbs = carbs;
		this.protein = protein; 
	}
	
	
	public int getFoodId(){
		return this.id; 
	}
	
	public String getFoodName() {
		return this.name; 
	}
	
	public String getFoodType() {
		return this.type; 
	}
	
	public int getCalories() {
		return this.calorie; 
	}
	
	public int getFat() {
		return this.fat;
	}
  	
	public int getCarbs() {
		return this.carbs; 
	}
	
	public int getProtein() {
		return this.protein; 
	}
	
	public float getPrice() {
		return this.price;
	}
	
	public String getType() {
		return this.type; 
	}
	
	public int getAmount() {
		return this.amount; 
	}

}
