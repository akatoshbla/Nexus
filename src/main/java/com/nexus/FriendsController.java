package com.nexus;

import static com.nexus.JsonUtility.json;
import static spark.Spark.*;

/**
 * Contains the API methods for friend operations.
 */
public class FriendsController {
	/**
	 * <p>Summary of API calls:</p>
	 * <p>GET /friendslist/:username
	 * <p>	- Returns the specified user's list of friends</p>
	 * <p>	- Returns a JSON: {"friends":[friends list]}</p>
	 * <br>
	 * <p>POST /friendRequest</p>
	 * 	<p>	- Sends a friend request from one user to another</p>
	 * 	<p>	- Request body should be a JSON with keys "from" and "to",</p>
	 * 	<p>		with the corresponding values being valid usernames.</p>
	 * 	<p>	- Returns a boolean denoting the success of the operation, </p>
	 * 	<p>		or null in the case of an error.</p>
	 * <br>
	 * <p>PUT /confirmFriend</p>
	 * <p>	- Confirms a pending friend request.</p>
	 * <p> 	- Request body is the same as in POST /friendRequest</p>
	 * <p>  - Returns a boolean denoting the success of the operation, </p>
	 * <p>		or null in the case of an error.</p>
	 * <br>
	 * <p>DELETE /deleteFriend</p>
	 * <p>	- Deletes a friendship between two users.</p>
	 * <p> 	- Request body is a JSON with keys "user1" and "user2", </p>
	 * <p> 		with the corresponding values being valid usernames.</p>
	 * <p> 	- Returns a boolean denoting the success of hte operation, </p>
	 * <p>		or null in the case of an error.</p>
	 * @param friendsService class 
	 * @throws Exception if error
	 */
	public FriendsController(final FriendsService friendsService) throws Exception{

		get("/friendsList/:name", (req, res) -> {
			try {
				return friendsService.getFriends(req.params(":name"));
			}
			catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		}, json());
		post("/friendRequest", (req, res) -> {
			try {
		
				return friendsService.friendRequest(req.body());
			}
			catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		}, json());
		put("/confirmFriend",(req,res) -> {
			try {
				return friendsService.confirmFriendRequest(req.body());
			}
			catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		},json());
		delete("/deleteFriend",(req,res) -> {
			try {
				return friendsService.deleteFriend(req.body());
			}
			catch (Exception e){
				e.printStackTrace();
				return null;
			}
		},json());
	}
}
