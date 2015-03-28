package edu.scu.oop.proj.dao;
import java.util.ArrayList;

//interface for Data Access Object with a few generic methods for interacting with database 
public interface DAO<T> {
	public void create(T object) throws RuntimeException;
	public void delete(T object) throws RuntimeException; 
	public T findElementById(int id) throws RuntimeException; 
	public ArrayList<T> findElements(int id) throws RuntimeException; 
	public void update(int id) throws RuntimeException; 
}


