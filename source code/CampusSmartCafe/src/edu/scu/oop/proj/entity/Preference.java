package edu.scu.oop.proj.entity;

public class Preference {
	private float preferred_fund;
	private int preferred_calories; 
	
	public Preference() {
		this.preferred_fund = 300; 
		this.preferred_calories = 2000; 
	}	
	
	public void setFundPreference(float fund) {
		this.preferred_fund = fund; 
	}
	
	public void setCaloriesPreference(int calories) {
		this.preferred_calories = calories; 
	}
	
	public float getFundPreference() {
		return this.preferred_fund; 
	}
	
	public int getCaloriesPreference() {
		return this.preferred_calories; 
	}
}
