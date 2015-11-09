package com.nexus;

import static com.nexus.JsonUtility.json;
import static spark.Spark.post;

import com.google.gson.Gson;

/**
 * This class has the posts and gets for loading and editing the
 * profile page of a valid user. 
 * @author David Kopp
 *
 */

public class ProfileController 
{
	/** 
	 * The post /profile takes nothing from the frontend, but it will check for a valid session.
	 * <p>Returns a Json with the following:
	 * <p>"session": true,
 	 * <p>"joined": "10/10/2015",
     * <p>"lastOnline": "10/20/2015",
     * <p>"realName": "John Doe",
     * <p>"role": "Newbie",
     * <p>"shares": 6,
     * <p>"likes": 12,
     * <p>"posts": 0,
     * <p>"followers": 0,
     * <p>"aboutDesc": "I am a computer science student at CSUN.",
     * <p>"userName": "user007",
     * <p>"avatar": "a base64 String"
     * <p>"currentGame": "League of Legends",
     * <p>"socialNames": "[\"Twitter\",\"Facebook\"]",
     * <p>"socialLinks": "[\"https://twitter.com/johndoe\",\"http://www.facebook.com/\"]",
     * <p>"gameNames": "[\"League of Legends\",\"World of Warcraft\"]"
	 * @param profileService class
	 */
	public ProfileController(final ProfileService profileService)
	{
		
		/**
		 * This method reloads the whole profile page on login or when switching
		 * back to the profile back. Takes no input, but does take a valid session.
		 * Return is a Json with the following as an example:
		 * "session": true,
  		 * "joined": "10/10,/2015",
         * "lastOnline": "10/20,/2015",
         * "realName": "John Doe",
         * "role": "Newbie",
         * "shares": 6,
         * "likes": 12,
         * "posts": 0,
         * "followers": 0,
         * "aboutDesc": "I am a computer science student at CSUN.",
         * "userName": "user007",
         * "avatar": "a base64 String"
         * "currentGame": "League of Legends",
         * "socialNames": "[\"Twitter\",\"Facebook\"]",
         * "socialLinks": "[\"https://twitter.com/johndoe\",\"http://www.facebook.com/\"]",
         * "gameNames": "[\"League of Legends\",\"World of Warcraft\"]"
		 */
		post("/profile",(req,res) -> {
			String username;
	
			try {
				if (req.session().attribute("username") != null)
				{
					username = req.session().attribute("username");
					System.out.println("Username: " + username);
					System.out.println("Has a session id: " + req.session().id());
				}
				else
				{
					username = null;
					System.out.println("Non-Session User Alert at " +req.ip());
				}
				return profileService.getUserProfile(username);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}		
		},json());
		
		//update from front end passed to middle tier to update database realName
		
		post("/realName", (req, res) -> {
			//set username
			String username;

			//creating new session if username doesn't = null
			try {
				if (req.session().attribute("username") != null) {
					//req.session requests username from cookie with the attribute of username
					username = req.session().attribute("username");
					System.out.println("Username: " + username);
					System.out.println("Has a session id: " + req.session().id());
				} else {
					username = null;
					System.out.println("Non-Session User Alert at " + req.ip());
				}
				//returns the result from getRealName which is in ProfileService class
				return profileService.getRealName(username, req.body());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			//transforms json object from java to json object from frontend
		} , json());
		
	}	
}