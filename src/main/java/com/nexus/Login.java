package com.nexus;

public class Login {
	
	private String userName;
	private String password;
	private boolean loggedIn;

	public Login(String name, String newPassword, boolean logStatus)
	{
		userName = name;
		password = newPassword;
		loggedIn = logStatus;
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
