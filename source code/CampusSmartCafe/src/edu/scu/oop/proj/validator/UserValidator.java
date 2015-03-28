package edu.scu.oop.proj.validator;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import edu.scu.oop.proj.dao.DAOFactory;
import edu.scu.oop.proj.dao.MySQLAccess;


public class UserValidator {

	//validate if the id and password matches what we have in the database 
	 public static boolean validateUserExist(String account, String password) throws SQLException {
		 	if (!isInteger(account)) {
		 		return false; 
		 	}
		 	boolean userExist = DAOFactory.getUserDAO().checkExist(Integer.parseInt(account), password); 
			
			if (userExist == false) {
			    return false; 
			} 
			return true; 
		}
	 
	 
	 //helper method to convert id from string to int 
	 public static boolean isInteger( String input ) {
		    try {
		        Integer.parseInt( input );
		        return true;
		    }
		    catch( Exception e ) {
		        return false;
		    }
		}
}
