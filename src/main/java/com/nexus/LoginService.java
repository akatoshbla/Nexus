package com.nexus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

//import java.util.ArrayList;
//import java.util.List;

public class LoginService {
	
	public boolean login(String username, String password) throws Exception
	{

    	//password=password.toUpperCase();
		NexusDB db = new NexusDB();
		password = db.hashPassword(password);
		String hash = db.retrievePassword(username);
		
		//failIfInvalid(password);
		if (hash.equals(password) && hash!=null)
			return true;	
		else	
			return false;		
	}
	
	public String test()
	{
		return "This is a test!";
	}
		
	public JsonObject loginResult(String body) throws Exception
	{

		User user = new Gson().fromJson(body, User.class);
		JsonObject jsonobj = new JsonObject();
		jsonobj.addProperty("result", login(user.getUsername(),user.getPassword()));
		return jsonobj;
	}
	
//	private void failIfInvalid(String password) {
//		if (password == null || password.isEmpty()) {
//			throw new IllegalArgumentException("Parameter 'password' cannot be empty");
//		}
//	}
}
