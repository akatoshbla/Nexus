package com.nexus;

public class Login {
	
	private String userName;
	private String password;
	private boolean loggedIn;

	public Login(String name, String newPassword, boolean loginStatus)
	{
		userName = name;
		password = newPassword;
		loggedIn = loginStatus;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public boolean getLoggedIn()
	{
		return loggedIn;
	}
}
