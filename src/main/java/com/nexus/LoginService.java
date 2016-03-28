package com.nexus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * This class has the methods to support the LoginController.
 * @author David Kopp
 *
 */
public class LoginService {
	
	/**
	 * This method checks to see if the passed username and password matches
	 * the information in the database.
	 * @param username String
	 * @param password String
	 * @return boolean True - successful, False - failed.
	 * @throws Exception if error 
	 */
	private boolean login(String username, String password) throws Exception
	{
		NexusDB db = new NexusDB();
		password = db.hashPassword(db.hashPassword(password).toLowerCase());
		String hash = db.retrievePassword(username);
		System.out.println(password);
		if (hash.equals(password) && hash!=null)
			return true;	
		else	
			return false;		
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
	 * This method calls login and wraps the resulting boolean into a json.
	 * @param body String
	 * @return Json True - successful, False - failed.
	 * @throws Exception if error
	 */
	public JsonObject loginResult(String body) throws Exception
	{

		User user = new Gson().fromJson(body, User.class);
		JsonObject jsonobj = new JsonObject();
		jsonobj.addProperty("result", login(user.getUsername(),user.getPassword()));
		return jsonobj;
	}
	
	/**
	 * This method returns the username from the user object.
	 * @param body String
	 * @return String username
	 * @throws Exception if error
	 */
	public String loginName(String body) throws Exception
	{
		User user = new Gson().fromJson(body, User.class);
		String username = user.getUsername(); 
		return username;
	}
	
//	private void failIfInvalid(String password) {
//		if (password == null || password.isEmpty()) {
//			throw new IllegalArgumentException("Parameter 'password' cannot be empty");
//		}
//	}
}