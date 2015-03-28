package edu.scu.oop.proj.entity;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import edu.scu.oop.proj.dao.DAOFactory;
import edu.scu.oop.proj.validator.UserValidator;


public class UserSession {
	 private static UserSession currSession; 
	 private User currUser; 
	 private UserValidator userValidator; 
	 private String userName; 
	 
	 //get the singleton instance 
	 public static synchronized UserSession getInstance() {
	        if ( currSession == null ) {
	        	currSession = new UserSession();
	        }
	        return currSession;
	    }
	 
	 //login the system if the userid and password matches
	 public boolean login(String id, String password) throws NumberFormatException, SQLException, UserSessionException {
		if (currUser != null) {
			throw new UserSessionException(currSession.currUser); 
		}
		userValidator = new UserValidator(); 
		boolean userExist = UserValidator.validateUserExist(id, password); 
		if (userExist) return true; 
		return false; 
	 }
	 
	 //logout the system with currSession.currUser set to NULL 
	 public void logout() throws NoUserLoggedinException{
		 if (currUser == null) {
			 throw new NoUserLoggedinException(); 
		 }
		 currSession.currUser = null; 
	 }
	 
	 public User getCurrentUser() {
			 return currUser;
	 }
	 
	 public void setCurrentUser(User currUser) {
		 this.currUser = currUser;
	 }
	 
	 
}

	
