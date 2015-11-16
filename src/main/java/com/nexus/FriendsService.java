package com.nexus;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
/**
 * This class contains the methods to support the FriendsController
 *
 */
public class FriendsService {

		/**
		 * Returns a JSON object containing a list usernames of all the user's friends.
		 * @param username String
		 * @return JsonObject
		 */
		public JsonObject getFriends(String username) {
			try {
				NexusDB db = new NexusDB();
				JsonObject jsonobj = new JsonObject();

				if(!db.recordExists(username))
					jsonobj.addProperty("error", "User doesnt exist.");
				
				else {
					JsonArray friendsArray = new JsonArray();
					
					ArrayList<String> friends = db.getFriendsList(username);
					
					for(String s: friends)
							friendsArray.add(new JsonPrimitive(s));
					
					jsonobj.add("friends", friendsArray);
				}
				return jsonobj;
			}
			catch (Exception e){
				e.printStackTrace();
				return null;
			}
		}
		/**
		 * Establishes a friend request between two users.
		 * Input is a JSON string containing the keys "to" and "from",
		 * 	with corresponding username values.
		 * @param body String
		 * @return Boolean
		 */
		public Boolean friendRequest(String body) {
			try{
				JsonObject jsonobj = new Gson().fromJson(body,JsonObject.class);
				String from = jsonobj.get("from").getAsString();
				String to = jsonobj.get("to").getAsString();
				NexusDB db = new NexusDB();
				return db.createFriendRequest(from, to);
			}
			catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		/**
		 * Confirms a pending friend request between two users.
		 * Input is a JSON string containing the keys "to" and "from",
		 * 	with corresponding username values.
		 * @param body String
		 * @return Booleans
		 */
		public Boolean confirmFriendRequest(String body){
			try{
				JsonObject jsonobj = new Gson().fromJson(body,JsonObject.class);
				String from = jsonobj.get("from").getAsString();
				String to = jsonobj.get("to").getAsString();
				NexusDB db = new NexusDB();
				return db.updateFriendStatus(from, to);
			}
			catch (Exception e){
				e.printStackTrace();
				return null;
			}
		}
		/**
		 * Deletes a friend relationship or pending friend request
		 * 	between two users.
		 * Input is a JSON string containing the keys "user1" and "user2", 
		 * 	with corresponding username values.
		 * @param body String
		 * @return Boolean
		 */
		public Boolean deleteFriend(String body){
			try {
				JsonObject jsonobj = new Gson().fromJson(body,JsonObject.class);
				String user1 = jsonobj.get("user1").getAsString();
				String user2 = jsonobj.get("user2").getAsString();
				NexusDB db = new NexusDB();
				return db.deleteFriend(user1, user2);
			}
			catch (Exception e){
				e.printStackTrace();
				return null;
			}
		}
}
