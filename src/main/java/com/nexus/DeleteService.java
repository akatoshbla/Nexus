package com.nexus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class DeleteService {
	public boolean delete(String username) throws Exception
	{
		NexusDB db = new NexusDB();
		return db.deleteUser(username);
		
	}
	
	public String test()
	{
		return "This is a test!";
	}
		
	public JsonObject deleteResult(String body) throws Exception
	{

		User user = new Gson().fromJson(body, User.class);
		JsonObject jsonobj = new JsonObject();
		jsonobj.addProperty("result", delete(user.getUsername()));
		return jsonobj;
	}
}
