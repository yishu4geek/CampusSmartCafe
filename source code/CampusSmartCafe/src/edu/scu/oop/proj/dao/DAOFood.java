package edu.scu.oop.proj.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import edu.scu.oop.proj.entity.Food;

public class DAOFood implements DAO<Food>{
	private static final String VENDING_MACHINE = "VendingMachine"; 
	private static final String CAFE = "Cafe"; 
	
	//get the list of food provided based on whether it's CAFE or Vending Machine
	public ArrayList<Food> findElementsByType(String store_type) throws RuntimeException {
		ArrayList<Food> foodLists = new ArrayList<Food>(); 
		try {
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			if (store_type == CAFE) {
				statement = (PreparedStatement) dao.getConn().prepareStatement(
						"SELECT *"
						+ "FROM food where type = 'CAFE'");
			}else if (store_type == VENDING_MACHINE) {
				statement = (PreparedStatement) dao.getConn().prepareStatement(
						"SELECT *"
						+ "FROM food where type = 'VM'");
			}
			ResultSet result = statement.executeQuery();
			System.out.println("food ok"); 
			while (result.next()) {
				int id = result.getInt(1); 
				String foodName = result.getString(2); 
				int amount = result.getInt(3); 
				String type = result.getString(4); 
				float price = result.getFloat(5); 
				int calorie = result.getInt(6); 
				int fat = result.getInt(7); 
				int carbs = result.getInt(8); 
				int protein = result.getInt(9); 
				Food currFood = new Food(id, foodName, amount, type, price, calorie, fat, carbs, protein); 
				foodLists.add(currFood); 
			}
			
		}catch (SQLException e) {
			throw new RuntimeException(); 
		}
		return foodLists;
	}
	
	//Get number of food for CAFE and VendingMachine 
	public int findNumOfItems(String store_type) throws RuntimeException {
		ArrayList<Food> foodLists = new ArrayList<Food>(); 
		int count = 0; 
		try {
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			if (store_type == CAFE) {
				statement = (PreparedStatement) dao.getConn().prepareStatement(
						"SELECT count(*)"
						+ "FROM food where type = 'CAFE'");
			}else if (store_type == VENDING_MACHINE) {
				statement = (PreparedStatement) dao.getConn().prepareStatement(
						"SELECT count(*)"
						+ "FROM food where type = 'VM'");
			}
			ResultSet result = statement.executeQuery();
			if (result.next()) {
				count = result.getInt(1); 
			}
		}catch (SQLException e) {
			throw new RuntimeException(); 
		}
		return count; 
	}
	
	//get the corresponding food name based on food id 
	public String getFoodName(int id) throws RuntimeException {
		String name = ""; 
		try {
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement(
					"SELECT name "
					+ "FROM food where id = ?");
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery(); 
			if(result.next()) {
				name = result.getString(1); 
			}
		}catch (SQLException e) {
			throw new RuntimeException(); 
		}
		return name;
	}

	@Override
	public void create(Food object) throws  UnsupportedOperationException, RuntimeException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(); 
	}

	@Override
	public void delete(Food object) throws  UnsupportedOperationException, RuntimeException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(); 
	}

	@Override
	public Food findElementById(int id) throws UnsupportedOperationException, RuntimeException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(); 
	}

	@Override
	public ArrayList<Food> findElements(int id) throws UnsupportedOperationException, RuntimeException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(); 
	}

	@Override
	public void update(int id) throws UnsupportedOperationException,RuntimeException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(); 
	}
	

}
