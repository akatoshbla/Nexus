package com.nexus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * This class is the service methods for CreateController.
 * @author David Kopp
 *
 */
public class CreateService {
	
	/**
	 * This method is used to hash the password and insert the name and hashed
	 * password into the database. Return is passed back into the createResult.
	 * @param username String
	 * @param password String
	 * @param email String
	 * @return boolean True - was successful, False - if failed
	 * @throws Exception if error
	 */
	private boolean create(String username, String password, String email) throws Exception
	{
		NexusDB db = new NexusDB();
		JforumDB jdb = new JforumDB();
		if (db.recordExists(username))
			return false;
		else {
			if (email != null)
			{
				return db.createUser(username,  db.hashPassword(
								db.hashPassword(password).toLowerCase()), email) && 
					  jdb.createUser(username, jdb.hashPassword(
								password,"MD5").toLowerCase(), email);
			}
			else
			{
				return db.createUser(username,  db.hashPassword(
							  db.hashPassword(password).toLowerCase())) &&
					  jdb.createUser(username, jdb.hashPassword(
							  password,"MD5").toLowerCase(),"");
			}
		}
	}
	
	/**
	 * Test method
	 * @return String
	 */
	public String test()
	{
		return "This is a test!";
	}
	
	/**
	 * This method is used to wrap the result of the create method's return in
	 * a json and passed back to the CreateController.
	 * @param body String
	 * @return Json with a boolean. True - successful, False - failed
	 * @throws Exception if error
	 */
	public JsonObject createResultr(String body) throws Exception
	{

		User user = new Gson().fromJson(body, User.class);
		JsonObject jsonobj = new JsonObject();
		jsonobj.addProperty("result", create(user.getUsername(),user.getPassword(), 
				user.getEmail()));
		return jsonobj;
	}
}