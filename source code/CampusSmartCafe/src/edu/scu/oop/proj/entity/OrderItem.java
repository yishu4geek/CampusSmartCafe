package edu.scu.oop.proj.entity;

public class OrderItem {
	private int food_id; 
	private int amount; 
	
	public OrderItem() {
		food_id = 0; 
		amount = 0; 
	}
	
	public OrderItem(int food_id, int amount) {
		this.food_id = food_id; 
		this.amount = amount; 
	}
	
	public int getFoodId() {
		return this.food_id; 
	}
	
	public int getFoodAmount() {
		return this.amount; 
	}
 }
