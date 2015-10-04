package com.nexus;

public class Login {
	
	private String userName;
	private String password;
	private boolean success;

	public Login(String name, String newPassword, boolean status)
	{
		userName = name;
		password = newPassword;
		success = status;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public boolean getSuccess()
	{
		return success;
	}
	
	public void setSuccess(boolean success)
	{
		this.success = success;
	}
}
