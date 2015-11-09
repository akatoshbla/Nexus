package com.nexus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.util.Collection;
import java.util.List;

import org.json.JSONArray;

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
			jsonobj.addProperty("followers", profile.getFollowers());
			jsonobj.addProperty("aboutDesc", profile.getAboutDesc());
			jsonobj.addProperty("userName", username);
			jsonobj.addProperty("avatar", profile.getAvatarPic());
			jsonobj.addProperty("currentGame", profile.getCurrentGame());
			Gson gsonContainer = new Gson();
			Collection<String> str = profile.getSocialNames();
			Collection<String> str2 = profile.getSocialLinks();
			Collection<String> str3 = profile.getGameLinks();
			jsonobj.addProperty("socialNames", gsonContainer.toJson(str));
			jsonobj.addProperty("socialLinks", gsonContainer.toJson(str2));
			jsonobj.addProperty("gameNames", gsonContainer.toJson(str3));
		}
		else {
			jsonobj.addProperty("session", false);
		}
		return jsonobj;
	}
	
	//method for realName
	public JsonObject getRealName(String username, String body) throws Exception{
		
		//creates a new profile and matches the body to the variable
		Profile profile = new Gson().fromJson(body, Profile.class);
		NexusDB db = new NexusDB();
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		
		//return object
		JsonObject jsonobj = new JsonObject();
		
		if(username != null) {
			//using the new profile to update it with getRealName
			profile = db.insertRealName(username, profile.getRealName());
			//populating jsonobj with new results from the profile
			jsonobj.addProperty("result", true);
			jsonobj.addProperty("realName", profile.getRealName());
		}
		else {
			jsonobj.addProperty("result", false);
		}
		return jsonobj;
	}
	
	/**
	 * This is a future method for taking a image object and converting 
	 * it into a blob to insert into the database.
	 * @param username String
	 * @return Json True - successful, False - failed
	 */
	public JsonObject setAvatar(String username)
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
