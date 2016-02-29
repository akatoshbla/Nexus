package com.nexus;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

/**
 * This class has the methods which support MessageController.
 * @author Alex Hall
 *
 */
public class MessageService {
	/**
	 * Sends a message from one user to another.
	 * @param body JSON string with keys "fromUser', "toUser", and "message"
	 * @return JsonObject result 
	 */
	public JsonObject sendMessage(String body) {
		JsonObject result = new JsonObject();
		try {
			NexusDB db = new NexusDB();
			JSONObject json = (JSONObject) new JSONParser().parse(body);
			//return db.updateMessages(message,fromUser,toUser); 
			Boolean val = db.updateMessages((String)json.get("message"),
					(String)json.get("fromUser"),(String)json.get("toUser"));
			result.addProperty("result",val);	
			return result;
		}
		catch (Exception e){
			result.addProperty("result",false);			
			return result;
		}
	}
	/**
	 * Returns a JSONArray containing a list of messages between two users.
	 * @param body JSON string with keys "fromUser" and "toUser"
	 * @return JSONArray
	 */
	public JSONArray getMessages(String body) {
		try {
			NexusDB db = new NexusDB();
			JSONObject json = (JSONObject) new JSONParser().parse(body);
			List<HashMap<String,Object>> messages = 
					db.getMessages((String)json.get("fromUser"),(String)json.get("toUser"));
			JSONArray arr = (JSONArray) new JSONParser().
					parse(JSONArray.toJSONString(messages));
			return arr;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
