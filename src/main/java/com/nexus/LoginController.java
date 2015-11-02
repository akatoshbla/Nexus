package com.nexus;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;

import spark.ModelAndView;

import static spark.Spark.*;
import static com.nexus.JsonUtility.*; 

/**
 * This class has the posts and gets for logging in a user and creating 
 * their session.
 * @author David Kopp
 *
 */
public class LoginController {
	
	/**
	 * Post /login takes two Strings username and password.
	 * <p>Returns a Json with a boolean and a cookie for sessions:
	 * <p>{"result": true}
	 * <p>cookie = JSESSIONID
	 * @param loginService class
	 */
	public LoginController(final LoginService loginService)  {
		
		/**
		 * Loop back test to check for a valid session
		 */
		get("/login", (req, res) -> {
			if (req.session().attribute("username") != null)
			{
				System.out.println("Username: " + req.session().attribute("username"));
				System.out.println("Has a session id: " + req.session().id());
			}
			else
			{
				System.out.println("Non-Session User Alert @ " + req.ip());
			}
			return loginService.test();
		}, json());
		
		/**
		 * This method checks to see if the user has a valid account
		 * and logs them in with a session.
		 * Returns a json with a boolean. True - successful, False - failed.
		 */
		post("/login",(req,res) -> {
			//add connection requested by printout
			Date date = new Date();
			System.out.println(date.toString() + ": " + req.ip());
			
			try {
				if (loginService.loginResult(req.body()).get("result").getAsBoolean()) 
				{
					String username = new Gson().fromJson(req.body(), User.class).getUsername();
					req.session().attribute("username", username); // TODO: Check sessionId = username
					System.out.println("User session id: " +req.session().id());
				}
				return loginService.loginResult(req.body());
			}
			catch (Exception e){
				e.printStackTrace();
				return null;
			}
		},json());
		
	}		
}