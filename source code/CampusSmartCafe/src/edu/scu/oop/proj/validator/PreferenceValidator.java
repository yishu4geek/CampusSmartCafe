package edu.scu.oop.proj.validator;

import java.util.ArrayList;

import edu.scu.oop.proj.dao.DAOFactory;
import edu.scu.oop.proj.entity.Food;
import edu.scu.oop.proj.entity.User;
import edu.scu.oop.proj.entity.OrderItem;
import edu.scu.oop.proj.entity.UserSession;

public class PreferenceValidator {
	private static final String VENDING_MACHINE = "VendingMachine"; 
	private static final String CAFE = "Cafe"; 
	
	//validate if the current order's calories consumption exceeds the preference amount left the user has 
	public boolean validateCaloriesPreference(User user, ArrayList<OrderItem> foodItems, String storeType) {
		int calories = this.calculateCalories(foodItems, storeType); 
		int calories_preference = DAOFactory.getUserDAO().findCaloriesPreferenceById(user.getId());
		int caloriesConsumed_vm = DAOFactory.getUserDAO().getCalorieConsumed_vm(UserSession.getInstance().getCurrentUser());
        int caloriesConsumed_cafe = DAOFactory.getUserDAO().getCalorieConsumed_Cafe(UserSession.getInstance().getCurrentUser());
    	int calorieLeft = calories_preference - caloriesConsumed_cafe - caloriesConsumed_vm;

		if (calories > calorieLeft) {
			return false; 
		}
		return true; 
	}
	
	//validate if the current order's total cash amount exceeds the preference amount left 
	public boolean validateFundPreference(User user, ArrayList<OrderItem> foodItems, String storeType) {
		float fund = this.calculateBill(foodItems, storeType); 
		float fund_preference = DAOFactory.getUserDAO().findFundPreferenceById(user.getId()); 
			
		float fundSpentVM = DAOFactory.getUserDAO().getFundSpendVM(UserSession.getInstance().getCurrentUser());
        float fundSpentCafe = DAOFactory.getUserDAO().getFundSpendCafe(UserSession.getInstance().getCurrentUser());
    	float fundLeft = fund_preference - fundSpentVM - fundSpentCafe;
		
		if (fund > fundLeft) {
			return false;  
		}
		return true; 
	}
	
	//calculate the total cash amount the current order has 
	public float calculateBill(ArrayList<OrderItem> foodItems, String storeType) {
		ArrayList<Food> foodLists = DAOFactory.getFoodDAO().findElementsByType(storeType); 
		float totalBill = 0; 
		float price = 0; 
		for (int i = 0; i < foodItems.size(); i++) {
			for (int j = 0; j < foodLists.size(); j++) {
				if (foodLists.get(j).getFoodId() == foodItems.get(i).getFoodId()) {
					price = foodLists.get(j).getPrice(); 
					break;
				}
			}
			if (storeType == CAFE) totalBill += price * foodItems.get(i).getFoodAmount(); 
			else totalBill += price; 
		}
		return totalBill; 
	}
	
	//calculate the total calories the current order has 
	public int calculateCalories(ArrayList<OrderItem> foodItems, String storeType) {
		ArrayList<Food> foodLists = DAOFactory.getFoodDAO().findElementsByType(storeType); 
		int totalCalories = 0; 
		int calorie = 0; 
		for (int i = 0; i < foodItems.size(); i++) {
			for (int j = 0; j < foodLists.size(); j++) {
				if (foodLists.get(j).getFoodId() == foodItems.get(i).getFoodId()) {
					calorie = foodLists.get(j).getCalories(); 
					break;
				}
			}
			if (storeType == CAFE) totalCalories += calorie * foodItems.get(i).getFoodAmount(); 
			else totalCalories += calorie; 
		}
		return totalCalories; 
	}
}
