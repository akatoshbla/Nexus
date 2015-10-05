package com.nexus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

//import java.util.ArrayList;
//import java.util.List;

public class LoginService {
		
	public boolean login(String password)
	{
		//failIfInvalid(password);
		if (password.equals("12345"))
		{
			return true;
		}
	else
		{
			return false;
		}
	}
	
	public String test()
	{
		return "This is a test!";
	}
		
	public JsonObject createLogin(String body)
	{
		Login login = new Gson().fromJson(body, Login.class);
		JsonObject jsonobj = new JsonObject();
		jsonobj.addProperty("result", login(login.getPassword()));
		
		return jsonobj;
	}
	
//	private void failIfInvalid(String password) {
//		if (password == null || password.isEmpty()) {
//			throw new IllegalArgumentException("Parameter 'password' cannot be empty");
//		}
//	}
}
