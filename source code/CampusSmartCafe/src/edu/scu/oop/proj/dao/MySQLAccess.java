package edu.scu.oop.proj.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class MySQLAccess {
	
	//configurations 
	 private static final String URL = "jdbc:mysql://localhost:3306/campus_smart_cafe"; 
	 private static final String DRIVER = "com.mysql.jdbc.Driver";
	 private static final String USER_NAME = "csc"; 
	 private static final String PASSWORD = "1234"; 
	 private static MySQLAccess db;
	 private Connection connect = null;
	 private Statement statement = null;
	 private PreparedStatement preparedStatement = null;
	 private ResultSet resultSet = null;
	 
	 //there's only one thread can access db at the same time  
	 public static synchronized MySQLAccess getDBConn() {
	        if ( db == null ) {
	            db = new MySQLAccess();
	        }
	        return db;
	    }
	 
	 //singleton
	 private MySQLAccess() {
		 try {
			 Class.forName(DRIVER);
			 connect = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		 }
		 catch (Exception e) {
			 e.printStackTrace();;
		 }
	 }
	 
	 private void close() {
		    try {
		      if (resultSet != null) {
		        resultSet.close();
		      }
		      if (statement != null) {
		        statement.close();
		      }
		      if (connect != null) {
		        connect.close();
		      }
		    } catch (Exception e) {

		    }
		  }
	 
	 public Connection getConn() {
		 return this.connect; 
	 }
	 

}
