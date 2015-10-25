package com.nexus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CreateService {
	public boolean create(String username, String password) throws Exception
	{
		NexusDB db = new NexusDB();
		password = db.hashPassword(password);
		if (db.recordExists(username))
			return false;
		else {
			return db.createUser(username, password);
		}
	}
	
	public String test()
	{
		return "This is a test!";
	}
		
	public JsonObject createResultr(String body) throws Exception
	{

		User user = new Gson().fromJson(body, User.class);
		JsonObject jsonobj = new JsonObject();
		jsonobj.addProperty("result", create(user.getUsername(),user.getPassword()));
		return jsonobj;
	}
}
