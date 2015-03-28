package edu.scu.oop.proj.entity;

import java.util.ArrayList;

public class Venue {
	private int id; 
	private String name; 
	private String type; 
	private String address;
	private ArrayList<Integer> foodLists;
	
	public Venue() {
		
	}
	
	public String getVenueType() {
		return this.type; 
	}
}
