package com.nexus;

/**
 * This is an Object to help encapsulate the user's data for the 
 * LoginService, CreateService, and DeleteService classes.
 * @author David Kopp
 *
 */
public class User {
	
	private String username;
	private String password;
	
	/**
	 * This is the user object contructor.
	 * @param username String
	 * @param password String
	 */
	public User(String username, String password)
	{
			this.username = username;
			this.password = password;
	}
	
	/**
	 * Getter for username.
	 * @return String
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * Getter for password.
	 * @return String
	 */
	public String getPassword()
	{
		return password;
	}
}
