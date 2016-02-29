package com.nexus;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	//	return null; //placeholder
	}
	/*
	public JsonObject messageResult(String body) throws ParseException {
		JSONObject json = (JSONObject) new JSONParser().parse(body);
		boolean val =  sendMessage((String)json.get("message"), (String)json.get("fromUser"),
				(String)json.get("toUser"));
		JsonObject result = new JsonObject();
		result.addProperty("result",val);
		return result;
	}*/
	public static void main(String[] args) throws ParseException {
		String obj = "{\"time\":\"test\",\"fromUser\":\"test5\","
				+ "\"toUser\":\"demotest3\",\"message\":\"hello\"}";
		MessageService ms = new MessageService();
	//	System.out.println(ms.sendMessage(obj)); 
		ms.getMessages(obj);
		
		
		/*
		String obj = "{\"messages\":[{\"time\":\"test\",\"from\":\"test1\","
				+ "\"to\":\"test2\",\"message\":\"hello\"},{\"time\":\"test\","
				+ "\"from\":\"test1\",\"to\":\"test2\",\"messge\":\"hello\"}]}";
		JSONObject json = (JSONObject) new JSONParser().parse(obj);
		System.out.println(json.get("messages"));
		JSONArray arr = (JSONArray)json.get("messages");
		System.out.println(arr);
		for(Object o: arr) {
			System.out.println( ((JSONObject)o).get("from") );
		}*/
	}
}
