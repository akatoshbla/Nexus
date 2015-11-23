package com.nexus;

import static com.nexus.JsonUtility.json;
import static spark.Spark.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
	 * Note: Profile get also works with /profile/&lt;username&gt; to bypass session checking (for testing).
	 * <ul>
	 * <li>Returns a Json with the following:
	 * <li>"session": true,
 	 * <li>"joined": "10/10/2015",
     * <li>"lastOnline": "10/20/2015",
     * <li>"realName": "John Doe",
     * <li>"role": "Newbie",
     * <li>"shares": 6,
     * <li>"likes": 12,
     * <li>"posts": 0,
     * <li>"followers": 0,
     * <li>"aboutDesc": "I am a computer science student at CSUN.",
     * <li>"userName": "user007",
     * <li>"avatar": "images/UserAvatars/defaultAvatar.png"
     * <li>"currentGame": "League of Legends",
     * <li>"socialNames": "[\"Twitter\",\"Facebook\"]",
     * <li>"socialLinks": "[\"https://twitter.com/johndoe\",\"http://www.facebook.com/\"]",
     * <li>"gameNames": "[\"League of Legends\",\"World of Warcraft\"]"
	 * </ul>
	 * <p>
	 * The post /realName takes a String from the frontend. Checks for a valid session, in which 
	 * it gets the user's name from.
	 * <ul>
	 * <li>Returns a Json with the following:
	 * <li>"result": true,
	 * <li>"realName": John Doe
	 * </ul>
	 * <p>
	 * The post /currentGame takes a String from the frontend. Checks for a valid session, in which
	 * it gets the uer's name from.
	 * <ul>
	 * <li>Returns a Json with the following:
	 * <li>"result": true,
	 * <li>"currentGame": Heros of the Storm
	 * </ul>
	 * <p>
	 * The post /userDesc takes a String from the frontend.
	 * <ul>
	 * <li>Returns a Json with the following:
	 * <li>"result": true,
     * <li>"userDesc": "I am a computer science student at CSUN."
	 * </ul>
	 * <p>
	 * The post /socialInfo takes two arrays from the frontend.
	 * <ul>
	 * <li>Returns a Json with the following:
	 * <li>"result": true,
     * <li>"socialNames": "[\"Twitter\",\"Facebook\"]",
     * <li>"socialLinks": "[\"http:/twitter.com/johndoe\",\"http://www.facebook.com/\"]"
     * </ul>
     * <p>
     * The post /favGames takes two arrays from the frontend.
     * <ul>
     * <li>Returns a Json with the following:
     * <li>"result": true,
     * <li>"socialNames": "[\"World of Warcraft\",\"League of Legends\"]",
     * <li>"socialLinks": "[\"http:/www.woldofwarcraft.com/\",\"http://www.leagueoflegends.com/\"]"
     * </ul>
     * <p>
     * The post /gamesPlayed takes one array from the frontend.
     * <ul>
     * <li>Returns a Json with the following:
     * <li>"result": true,
     * <li>"gamesPlayed": "[\"World of Warcraft\",\"League of Legends\"]"
     * </ul>
     * <p>
     * The post /avatar will take a File from the frontend.
     * <ul>
     * <li>Returns a json with the following:
     * <li>"avatar": "images/UserAvatars/defaultAvatar.png"
     * </ul>
     * @param profileService class
	 */
	public ProfileController(final ProfileService profileService)
	{
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
		 * This method reloads the whole profile page on login or when switching
		 * back to the profile back. Takes no input, but does take a valid session.
		 */
		get("/profile/:username",(req,res) -> {
			String username;
		
			try {
				String name = req.params(":username");
				
				if (req.session().attribute("username") != null)
					
				{
					username = req.session().attribute("username");
					System.out.println("Username: " + username);
					System.out.println("Has a session id: " + req.session().id());
				}
				else if (name != null){
					username=name;
					System.out.println("body username:" + name);
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