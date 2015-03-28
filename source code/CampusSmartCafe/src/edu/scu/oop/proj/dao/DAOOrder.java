package edu.scu.oop.proj.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Random;

import com.mysql.jdbc.PreparedStatement;

import edu.scu.oop.proj.entity.Food;
import edu.scu.oop.proj.entity.Order;
import edu.scu.oop.proj.entity.User;


public class DAOOrder implements DAO<Order>  {
	private Order order;
	private static final String VENDING_MACHINE = "VendingMachine"; 
	private static final String CAFE = "Cafe"; 

	//find the list of food information (name, price, calories etc) for the current order 
	public ArrayList<Food> findElements(Order currOrder) throws RuntimeException {
		ArrayList<Food> foodLists = new ArrayList<Food>(); 
		this.order = currOrder; 
		try {
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			for (int i = 0; i < order.getOrderItems().size(); i++) {	
				statement = (PreparedStatement) dao.getConn().prepareStatement(
						"SELECT * FROM food "
						+ "WHERE id = ?");
				int id = this.order.getOrderItems().get(i).getFoodId(); 
				statement.setInt(1, id);
				ResultSet resultSet = statement.executeQuery(); 
				while (resultSet.next()) {
					String foodName = resultSet.getString(2); 
					int amount = resultSet.getInt(3); 
					String type = resultSet.getString(4); 
					float price = resultSet.getFloat(5); 
					int calorie = resultSet.getInt(6); 
					int fat = resultSet.getInt(7); 
					int carbs = resultSet.getInt(8); 
					int protein = resultSet.getInt(9); 
					Food currFood = new Food(id, foodName, amount, type, price, calorie, fat, carbs, protein); 
					foodLists.add(currFood); 
				}
			}
		}catch (SQLException e) {
			throw new RuntimeException(); 
		}
		return foodLists;
	}
	
	//create the order for the current user in the database 
	public void create(User currUser) throws RuntimeException {
	//	ArrayList<Food> foods = findElements(currUser.getOrder()); 
		try {
			this.order = currUser.getOrder();
			int order_id = order.getID(); 
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement1 = null; 
			PreparedStatement statement2 = null; 

			for (int i = 0; i < order.getOrderItems().size(); i++) {	
				statement1 = (PreparedStatement) dao.getConn().prepareStatement(
						"SELECT * FROM food "
						+ "WHERE id = ?");
				statement1.setInt(1, order.getOrderItems().get(i).getFoodId());
				ResultSet result1 = statement1.executeQuery(); 
				if (result1.next()) {
					float price = result1.getFloat(5); 
					int calorie = result1.getInt(6);
					statement2 = (PreparedStatement) dao.getConn().prepareStatement("INSERT INTO user_order VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
					
					statement2.setInt(1, order_id);
					statement2.setInt(2, currUser.getId());
					statement2.setInt(3, order.getOrderItems().get(i).getFoodId());
					statement2.setInt(4, order.getOrderItems().get(i).getFoodAmount()); 
					statement2.setString(5, order.getLocation());
					statement2.setTimestamp(6, getCurrentTimeStamp());
					statement2.setFloat(7, price);
					statement2.setInt(8, calorie); 
										
					statement2.executeUpdate(); 
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(); 
		}
	}
	
	//get the address where the user should pick up the food based on which venue the user placed the order 
	public String getOrderVenue(User user) throws RuntimeException{
		String venue = ""; 
		try {
			String location = user.getOrder().getLocation(); 
			MySQLAccess dao = MySQLAccess.getDBConn();
			PreparedStatement statement = null; 
			statement = (PreparedStatement) dao.getConn().prepareStatement(
					"SELECT address FROM food_provider "
					+ "WHERE name = ?");
			statement.setString(1, location);
			ResultSet result = statement.executeQuery(); 
			if (result.next()) {
				venue = result.getString(1); 
			}
		}catch (SQLException e) {
			throw new RuntimeException(); 
		}
		return venue; 
	}
	
	@Override
	public void create(Order order) throws UnsupportedOperationException, RuntimeException {
		throw new UnsupportedOperationException(); 
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(Order object) throws UnsupportedOperationException, RuntimeException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(); 
	}

	@Override
	public Order findElementById(int id) throws UnsupportedOperationException, RuntimeException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(); 
	}

	@Override
	public ArrayList<Order> findElements(int id) throws UnsupportedOperationException,RuntimeException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(); 
	}

	@Override
	public void update(int id) throws UnsupportedOperationException, RuntimeException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException(); 
	}
	
	private static java.sql.Timestamp getCurrentTimeStamp() {
	    java.util.Date today = new java.util.Date();
	    return new java.sql.Timestamp(today.getTime());
	}
	
}
