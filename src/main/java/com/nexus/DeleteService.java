package com.nexus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * This class has the methods to support the DeleteController.
 * @author David Kopp
 *
 */
public class DeleteService {
	
	/**
	 * This removes the user from the database.
	 * @param username String
	 * @return boolean True - successful, False - failed
	 * @throws Exception if error
	 */
	private Boolean deactivate(String username) throws Exception
	{
		NexusDB db = new NexusDB();
		return db.deactivateUser(username);
		
	}
	private Boolean activate(String username) throws Exception
	{
		NexusDB db = new NexusDB();
		return db.activateUser(username);
	}
	/**
	 * This method is for testing.
	 * @return String
	 */
			
	public String test()
	{
		return "This is a test!";
	}
	
	/**
	 * This method wraps the result of activate into a json and passes it back
	 * to the DeleteController.
	 * @param body String
	 * @return JsonObject with a boolean. True - was success, False - failed.
	 * @throws Exception if error
	 */
	public JsonObject activateResult(String body) throws Exception
	{
		User user = new Gson().fromJson(body, User.class);
		JsonObject jsonobj = new JsonObject();
		jsonobj.addProperty("result", activate(user.getUsername()));
		return jsonobj;
	}
	/**
	 * This method wraps the result of deactivate into a json and passes it back
	 * to the DeleteController.
	 * @param body String
	 * @return JsonObject with a boolean. True - was success, False - failed.
	 * @throws Exception if error
	 */
	public JsonObject deactivateResult(String body) throws Exception
	{

		User user = new Gson().fromJson(body, User.class);
		JsonObject jsonobj = new JsonObject();
		jsonobj.addProperty("result", deactivate(user.getUsername()));
		return jsonobj;
	}
}
