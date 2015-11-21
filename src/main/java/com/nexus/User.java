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
	private String email;
	
	/**
	 * This is the user object contructor.
	 * @param username String
	 * @param password String
	 * @param email String
	 */
	public User(String username, String password, String email)
	{
			this.username = username;
			this.password = password;
			this.email = email;
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
	
	/**
	 * Getter for email.
	 * @return String
	 */
	public String getEmail() 
	{
		return email;
	}
}
