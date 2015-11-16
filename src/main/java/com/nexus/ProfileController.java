package com.nexus;

import static com.nexus.JsonUtility.json;
import static spark.Spark.*;

/**
 * This class has the posts and gets for loading and editing the
 * profile page of a valid user. 
 * @author David Kopp
 *
 */
public class ProfileController 
{
	/**
	 * The get /profile takes nothing from the frontend, but it will check for a valid session.
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
	 * 
	 * The post /realName takes a String from the frontend. Checks for a valid session, in which 
	 * it gets the user's name from.
	 * <p>Returns a Json with the following:
	 * <p>"result": true,
	 * <p>"realName": John Doe
	 * 
	 * The post /currentGame takes a String from the frontend. Checks for a valid session, in which
	 * it gets the uer's name from.
	 * <p>Returns a Json with the following:
	 * <p>"result": true,
	 * <p>"currentGame": Heros of the Storm
	 * 
	 * The post /userDesc takes a String from the frontend.
	 * <p>Returns a Json with the following:
	 * <p>"result": true,
     * <p>"userDesc": "I am a computer science student at CSUN."
	 * 
	 * The post /socialInfo takes two arrays from the frontend.
	 * <p>Returns a Json with the following:
	 * <p>"result": true,
     * <p>"socialNames": "[\"Twitter\",\"Facebook\"]",
     * <p>"socialLinks": "[\"http:/twitter.com/johndoe\",\"http://www.facebook.com/\"]"
     * 
     * The post /favGames takes two arrays from the frontend.
     * <p>Returns a Json with the following:
     * <p>"result": true,
     * <p>"socialNames": "[\"World of Warcraft\",\"League of Legends\"]",
     * <p>"socialLinks": "[\"http:/www.woldofwarcraft.com/\",\"http://www.leagueoflegends.com/\"]"
     * 
     * The post /gamesPlayed takes one array from the frontend.
     * <p>Returns a Json with the following:
     * <p>"result": true,
     * <p>"gamesPlayed": "[\"World of Warcraft\",\"League of Legends\"]"
     * 
     * The post /avatar will take a File from the frontend.
     * <p>Returns a json with the following:
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
         * "realName": "John Doe",        (mutable)(done) 
         * "role": "Newbie",
         * "shares": 6,
         * "likes": 12,
         * "posts": 0,
         * "followers": 0,
         * "aboutDesc": "I am a computer science student at CSUN.",			(mutable)
         * "userName": "user007",
         * "avatar": "link here"		(mutable)
         * "currentGame": "League of Legends",		(mutable)
         * "socialNames": "[\"Twitter\",\"Facebook\"]",		(mutable)
         * "socialLinks": "[\"https://twitter.com/johndoe\",\"http://www.facebook.com/\"]",		(mutable)
         * "gameNames": "[\"League of Legends\",\"World of Warcraft\"]"		(mutable)
		 */
		get("/profile",(req,res) -> {
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
				e.printStackTrace();
				return null;
			}		
		}, json());
		
				/**
				 * 
				 */
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
						return profileService.updateRealName(username, req.body());
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					// transforms json object from java to json object from frontend
				}, json());
				
				post("/currentGame", (req, res) -> { 
					String username;
					
					try {
						if (req.session().attribute("username") != null) {
							username = req.session().attribute("username");
							System.out.println("Username: " + username);
							System.out.println("Has a session id: " + req.session().id());
						} else {
							username = null;
							System.out.println("Non-Session User Alert at " + req.ip());	
						}
						return profileService.updateCurrentGame(username, req.body());
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}, json());
				
				post("/socialInfo", (req, res) -> {
					String username;
					
					try {
						if (req.session().attribute("username") != null) {
							username = req.session().attribute("username");
							System.out.println("Username: " + username);
							System.out.println("Has a session id: " + req.session().id());
						} else {
							username = null;
							System.out.println("Non-Session User Alert at " + req.ip());	
						}
						return profileService.updateSocialGames(username, req.body());
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}, json());
				
				post("/favGames", (req, res) -> {
					String username;
					
					try {
						if (req.session().attribute("username") != null) {
							username = req.session().attribute("username");
							System.out.println("Username: " + username);
							System.out.println("Has a session id: " + req.session().id());
						} else {
							username = null;
							System.out.println("Non-Session User Alert at " + req.ip());	
						}
						return profileService.updateFavGames(username, req.body());
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}, json());
				
				post("gamesPlayed", (req, res) -> {
					String username;
					
					try {
						if (req.session().attribute("username") != null) {
							username = req.session().attribute("username");
							System.out.println("Username: " + username);
							System.out.println("Has a session id: " + req.session().id());
						} else {
							username = null;
							System.out.println("Non-Session User Alert at " + req.ip());	
						}
						return profileService.updateGamesPlayed(username, req.body());
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}, json());
				
				post("/userDesc", (req, res) -> {
					String username;
					
					try {
						if (req.session().attribute("username") != null) {
							username = req.session().attribute("username");
							System.out.println("Username: " + username);
							System.out.println("Has a session id: " + req.session().id());
						} else {
							username = null;
							System.out.println("Non-Session User Alert at " + req.ip());	
						}
						return profileService.updateUserDesc(username, req.body());
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}, json());
				
				post("/avatar", (req, res) -> {
					String username;
					
					try {
						if (req.session().attribute("username") != null) {
							username = req.session().attribute("username");
							System.out.println("Username: " + username);
							System.out.println("Has a session id: " + req.session().id());
						} else {
							username = null;
							System.out.println("Non-Session User Alert at " + req.ip());	
						}
						return profileService.updateAvatar(username, req.body());
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}, json());
	}	
}