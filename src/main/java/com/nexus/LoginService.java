package com.nexus;

//import java.util.ArrayList;
//import java.util.List;

public class LoginService {
	
	//private ArrayList<Login> userLogged = new ArrayList<Login>();
	Login login;
	
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
	
//	public List<Login> getAll()
//	{
//		return userLogged;
//	}
	public Boolean isSuccess()
	{
		if (login.getSuccess())
		{
			login.setSuccess(false);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean createLogin(String name, String password)
	{
		login = new Login(name, password, login(password));
		//userLogged.add(log);
		
		return login.getSuccess();
	}
	
	private void failIfInvalid(String password) {
		if (password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Parameter 'password' cannot be empty");
		}
	}
}
