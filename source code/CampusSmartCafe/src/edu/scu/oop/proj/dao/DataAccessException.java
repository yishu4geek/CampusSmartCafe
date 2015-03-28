package edu.scu.oop.proj.dao;
import java.sql.SQLException;


public class DataAccessException extends SQLException {
	public DataAccessException () {
		super("Data Access Exception"); 
	}
}

