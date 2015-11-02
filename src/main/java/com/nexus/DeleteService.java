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
	private boolean delete(String username) throws Exception
	{
		NexusDB db = new NexusDB();
		return db.deleteUser(username);
		
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
	 * This method wraps the result of delete into a json and passes it back
	 * to the DeleteController.
	 * @param body String
	 * @return JsonObject with a boolean. True - was success, False - failed.
	 * @throws Exception if error
	 */
	public JsonObject deleteResult(String body) throws Exception
	{

		User user = new Gson().fromJson(body, User.class);
		JsonObject jsonobj = new JsonObject();
		jsonobj.addProperty("result", delete(user.getUsername()));
		return jsonobj;
	}
}
