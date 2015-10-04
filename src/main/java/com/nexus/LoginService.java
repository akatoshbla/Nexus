package com.nexus;

import java.util.ArrayList;
import java.util.List;

public class LoginService {
	
	private ArrayList<Login> userLogged = new ArrayList<Login>();
	
	public boolean login(String password)
	{
		failIfInvalid(password);
		if (password.equals("12345"))
		{
			return true;
		}
	else
		{
			return false;
		}
	}
	
	public List<Login> getAll()
	{
		return userLogged;
	}
	
	public Login createLogin(String name, String password)
	{
		Login log = new Login(name, password, login(password));
		userLogged.add(log);
		
		return log;
	}
	
	private void failIfInvalid(String password) {
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Parameter 'password' cannot be empty");
		}
	}
}
