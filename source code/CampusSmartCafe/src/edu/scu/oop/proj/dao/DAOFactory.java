package edu.scu.oop.proj.dao;
import java.util.ArrayList;

//Factory class. All interaction with database needs to be handled via this DAOFactory class 
final public class DAOFactory {
	
	private static DAOUser userDAO = null;
	private static DAOOrder orderDAO = null;
	private static DAOFood foodDAO = null; 
	
	public static DAOUser getUserDAO() {
		if (userDAO == null) {
			userDAO = new DAOUser();
		}
		return userDAO;
	}
	
	public static DAOOrder getOrderDAO() {
		if (orderDAO == null) {
			orderDAO = new DAOOrder();
		}
		return orderDAO; 
	}
	
	public static DAOFood getFoodDAO() {
		if (foodDAO == null) {
			foodDAO = new DAOFood(); 
		}
		return foodDAO; 
	}
//}
}

