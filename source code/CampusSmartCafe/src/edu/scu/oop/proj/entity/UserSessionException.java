package edu.scu.oop.proj.entity;

public class UserSessionException extends Exception {
	User currUser;
	public UserSessionException () {super ("UserSessionException");}
	public UserSessionException (User user) {
		currUser = user; 
	}
    public String toString(){
       return("User " + currUser.getName() + " already logged in");
    }
}
