package com.nexus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class has all the methods to support the ProfileController.
 * @author David Kopp
 *
 */
public class ProfileService 
{
	/**
	 * This method gets all the information from the database for the profile page
	 * and wraps it all up in a json for the ProfileController and frontend.
	 * @param username String
	 * @return Json
	 * @throws Exception if error
	 */
	public JsonObject getUserProfile(String username) throws Exception
	{
		NexusDB db = new NexusDB();
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		JsonObject jsonobj = new JsonObject();
		
		if (username != null) {
			Profile profile = db.getProfile(username);
			jsonobj.addProperty("session", true);
			jsonobj.addProperty("joined", profile.getJoined());
			jsonobj.addProperty("lastOnline", profile.getLastOnline());
			jsonobj.addProperty("realName", profile.getRealName());
			jsonobj.addProperty("role", profile.getRole());
			jsonobj.addProperty("shares", profile.getShares());
			jsonobj.addProperty("likes", profile.getLikes());
			jsonobj.addProperty("posts", profile.getPosts());
			jsonobj.addProperty("friends", profile.getFriends());
			jsonobj.addProperty("userDesc", profile.getUserDesc());
			jsonobj.addProperty("userName", username);
			jsonobj.addProperty("avatar", profile.getAvatar());
			jsonobj.addProperty("currentGame", profile.getCurrentGame());
			Gson gsonContainer = new Gson();
			Collection<String> str = profile.getSocialNames();
			Collection<String> str2 = profile.getSocialLinks();
			Collection<String> str3 = profile.getFavGameNames();
			Collection<String> str4 = profile.getFavGameLinks();
			Collection<String> str5 = profile.getSupportedGames();
			jsonobj.addProperty("socialNames", gsonContainer.toJson(str));
			jsonobj.addProperty("socialLinks", gsonContainer.toJson(str2));
			jsonobj.addProperty("favGameNames", gsonContainer.toJson(str3));
			jsonobj.addProperty("favGameLinks", gsonContainer.toJson(str4));
			jsonobj.addProperty("gameNames", gsonContainer.toJson(str5));
		}
		else {
			jsonobj.addProperty("session", false);
		}
		return jsonobj;
	}
	
		/**
		 * This method updates the realName in the userProfile.
		 * @param username String
		 * @param body String
		 * @return JsonObject
		 * @throws Exception if error
		 */
		public JsonObject updateRealName(String username, String body) throws Exception{
			
			//creates a new profile and matches the body to the variable
			Profile profile = new Gson().fromJson(body, Profile.class);
			NexusDB db = new NexusDB();
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
			
			//return object
			JsonObject jsonobj = new JsonObject();
			
			if(username != null) {
				String realName = db.updateUserProfile(username, "realName", profile.getRealName());
				//populating jsonobj with new results from the profile
				jsonobj.addProperty("result", true);
				jsonobj.addProperty("realName", realName);
			}
			else {
				jsonobj.addProperty("result", false);
			}
			return jsonobj;
		}
		
		/**
		 * This method updates the current game played in the user Profile
		 * @param username String
		 * @param body String
		 * @return JsonObject
		 * @throws Exception if error
		 */
		public JsonObject updateCurrentGame(String username, String body) throws Exception {
			Profile profile = new Gson().fromJson(body, Profile.class);
			NexusDB db = new NexusDB();
			JsonObject jsonobj = new JsonObject();
			
			if (username != null) {
				jsonobj.addProperty("result", true);
				jsonobj.addProperty("currentGame", db.updateUserProfile(username, "currentGame", profile.getCurrentGame()));
			}
			else {
				jsonobj.addProperty("result", false);
			}
			return jsonobj;
		}
		
		/**
		 * This method updates the social table. Deletes all the records for the user and reinserts new ones.
		 * @param username String
		 * @param body String
		 * @return JsonObject
		 * @throws Exception if error
		 */
		public JsonObject updateSocialGames(String username, String body) throws Exception {
			Profile profile = new Gson().fromJson(body, Profile.class);
			NexusDB db = new NexusDB();
			JsonObject jsonobj = new JsonObject();
			
			
			if (username != null) {
				profile = db.updateUserProfileLists(username, "socialLinks", "socialName" , "link" , 
						profile.getSocialNames(), profile.getSocialLinks());
				jsonobj.addProperty("result", true);
				Gson gsonContainer = new Gson();
				Collection<String> str = profile.getSocialNames();
				Collection<String> str2 = profile.getSocialLinks();
				jsonobj.addProperty("socialNames", gsonContainer.toJson(str));
				jsonobj.addProperty("socialLinks", gsonContainer.toJson(str2));
			}
			else {
				jsonobj.addProperty("result", false);
			}
			
			return jsonobj;
		}
		
		/**
		 * This method updates the favGames table. Deletes all the records for the user and reinserts new ones.
		 * @param username String
		 * @param body String
		 * @return JsonObject
		 * @throws Exception if error
		 */
		public JsonObject updateFavGames(String username, String body) throws Exception {
			Profile profile = new Gson().fromJson(body, Profile.class);
			NexusDB db = new NexusDB();
			JsonObject jsonobj = new JsonObject();
			
			
			if (username != null) {
				profile = db.updateUserProfileLists(username, "favGames", "gameName" , "gameLink" , 
						profile.getFavGameNames(), profile.getFavGameLinks());
				jsonobj.addProperty("result", true);
				Gson gsonContainer = new Gson();
				Collection<String> str = profile.getSocialNames();
				Collection<String> str2 = profile.getSocialLinks();
				jsonobj.addProperty("socialNames", gsonContainer.toJson(str));
				jsonobj.addProperty("socialLinks", gsonContainer.toJson(str2));
			}
			else {
				jsonobj.addProperty("result", false);
			}
			
			return jsonobj;
		}
		
		/**
		 * This method updates the gamesPlayed table. Deletes all the records for the user and reinserts new ones.
		 * @param username String
		 * @param body String
		 * @return JsonObject
		 * @throws Exception if error
		 */
		public JsonObject updateGamesPlayed(String username, String body) throws Exception {
			Profile profile = new Gson().fromJson(body, Profile.class);
			NexusDB db = new NexusDB();
			JsonObject jsonobj = new JsonObject();
			
			
			if (username != null) {
				ArrayList<String> result = db.updateUserProfileList(username, "gamesSupported", "gameName", profile.getSupportedGames());
				jsonobj.addProperty("result", true);
				Gson gsonContainer = new Gson();
				Collection<String> str = result;
				jsonobj.addProperty("gamesPlayed", gsonContainer.toJson(str));
			}
			else {
				jsonobj.addProperty("result", false);
			}
			
			return jsonobj;
		}
		
		/**
		 * This method updates the userDesc in the user profile table.
		 * @param username String
		 * @param body String
		 * @return JsonObject
		 * @throws Exception if error
		 */
		public JsonObject updateUserDesc(String username, String body) throws Exception {
			Profile profile = new Gson().fromJson(body, Profile.class);
			NexusDB db = new NexusDB();
			JsonObject jsonobj = new JsonObject();
			
			
			if (username != null) {
				String result = db.updateUserProfile(username, "userDesc", profile.getUserDesc());
				jsonobj.addProperty("result", true);
				jsonobj.addProperty("userDesc", result);
			}
			else {
				jsonobj.addProperty("result", false);
			}
			
			return jsonobj;
		}
	
	/**
	 * This is a future method for taking a image object and saving it to the server. Puts link in the database.
	 * @param username String
	 * @param body String
	 * @return Json True - successful, False - failed
	 * @throws Exception if error
	 */
	public JsonObject updateAvatar(String username, String body) throws Exception
	{
		NexusDB db = new NexusDB();
		JsonObject jsonobj = new JsonObject();
//		String filePath = "D:/Photos/Tom.jpg";
//		InputStream inputStream = new FileInputStream(new File(filePath));
//		 
//		String sql = "INSERT INTO person (photo) values (?)";
//		PreparedStatement statement = connection.prepareStatement(sql);
//		statement.setBlob(1, inputStream);
//		statement.executeUpdate();
		return jsonobj;
	}
}