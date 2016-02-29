package com.nexus;

import java.sql.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import java.util.Properties;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;

/**
 * This class contains the database methods.
 * @author David Kopp
 *
 */

public class NexusDB {

	private final String userName = "root";
	private final String password = "1234abcd";
	private final String serverName = "localhost";
	private final int portNumber = 3306;
	private final String dbName = "nexus";
	//private final String tableName = "users";


	//private ResultSet resultSet = null;

	/**
	 * Hashes a string using SHA-256
	 * @param password String
	 * @return String
	 * @throws Exception if error
	 */
	public String hashPassword(String password) throws Exception  {
		try 
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes("UTF-8"));
			byte[] digest = md.digest();
			return javax.xml.bind.DatatypeConverter.printHexBinary(digest).toString(); //converts byte array to hex string
		}
		catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * Connects to the database
	 * @return Connection
	 * @throws SQLException if error
	 */
	private Connection getConnection() throws SQLException
	{
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		Connection conn = DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + this.dbName,
				connectionProps);
		return conn;
	}
	/**
	 * Checks if there's a pending friend request
	 * 	from one user to another.
	 * @param fromUser String
	 * @param toUser String
	 * @return Boolean
	 * @throws SQLException if error
	 */
	
	public Boolean checkFriendRequest(String fromUser, String toUser) throws Exception
	{
		int id1= getUserId(fromUser);
		int id2= getUserId(toUser);
		if (id1 == -1 || id2 == -1)
				return null; 
		String query = "SELECT id FROM friends WHERE reqFrom=? AND "
				+ "reqTo=?";
		List<Integer> params = asList(id1,id2);
		List<HashMap<String,Object>> result = queryHelper(query, params);
		return (result==null)? null : (result.size()>0);
	}
	/**
	 * Checks if two users are friends.
	 * @param name1 String
	 * @param name2 String
	 * @return Boolean
	 * @throws SQLException if error
	 */
	
	public Boolean checkFriend(String name1, String name2) throws Exception
	{
		int id1= getUserId(name1);
		int id2= getUserId(name2);
		if (id1 == -1 || id2 == -1)
				return null; 
		if (id1 > id2)
		{
			int tmp = id1;
			id1 = id2;
			id2 = tmp;
		}
		String query = "SELECT id,friendId FROM friends WHERE id=? AND "
				+ "friendId=? AND reqFrom IS NULL AND reqTo IS NULL";
		
		List<Integer> params = asList(id1,id2);
		List<HashMap<String,Object>> result = queryHelper(query, params);
		return (result==null)? null : (result.size()>0);

	}
	/**
	 * Updates two users' friend status from pending to friends.
	 * @param name1 String
	 * @param name2 String
	 * @return Boolean
	 * @throws SQLException if error
	 */
	//TODO make sure they're not already friends or pending friends
	//TODO decrement the correct pending friend counter
	public Boolean updateFriendStatus(String name1, String name2) throws Exception
	{
		String statement = "UPDATE friends SET reqFrom=NULL,reqTo=NULL where id=? AND friendId=?";
		int id1= getUserId(name1);
		int id2= getUserId(name2);
		if (id1 == -1 || id2 == -1)
			return null;
		if (id1 > id2)
		{
			int tmp = id1;
			id1 = id2;
			id2 = tmp;
		}
		List<Integer> params = asList(id1,id2);
		updateHelper(statement,params);
		
		//TODO finish 
		//statement = "UPDATE userprofile SET friends = friends + 1 WHERE id = ? OR id = ?";
		//updateHelper(statement,params);
		
		return checkFriend(name1,name2);		
	}
	/**
	 * Deletes a friend relationship between two users.
	 * Works with friends or pending friends.
	 * @param name1 String
	 * @param name2 String
	 * @return Boolean
	 * @throws SQLException if error
	 */
	//TODO check if a friend relationship or pending friend relationship
	// is being deleted, and decrement the correct userprofile counter
	public Boolean deleteFriend(String name1,String name2) throws Exception
	{
		int id1= getUserId(name1);
		int id2= getUserId(name2);
		if(id1 == -1 || id2 == -1 || !checkFriend(name1,name2))	
			return null;
		if (id1 > id2)
		{
			int tmp = id1;
			id1 = id2;
			id2 = tmp;
		}
		String statement = "DELETE FROM friends WHERE id=? AND friendId=?";
		List<Integer> params = asList(id1,id2);
		updateHelper(statement,params);
		
		//TODO finish
		//statement = "UPDATE userprofile SET friends = friends - 1 WHERE id = ? OR id = ?";
		//updateHelper(statement,params);
		return !checkFriend(name1,name2);
	}

	/**
	 * Updates the database to indicate a pending friend request from one user to another.
	 * @param fromUser String
	 * @param toUser String
	 * @return Boolean
	 * @throws SQLException if error
	 */
	//TODO check to see if they're not already friends or pending friends
	public Boolean  createFriendRequest(String fromUser, String toUser) throws Exception
	{
		String statement = "INSERT into friends VALUES(?,?,?,?)";
		int fromId = getUserId(fromUser);
		int toId = getUserId(toUser);
		if (fromId == -1 || toId == -1)
				return null;
		int lowerId,higherId;
		if (fromId<toId)
		{
			lowerId=fromId;
			higherId=toId;
		}
		else 
		{
			lowerId=toId;
			higherId=fromId;
		}
		List<Integer> params = asList(lowerId,higherId,fromId,toId);
		updateHelper(statement, params);
		
		//TODO finish
		//params = asList(toId);
		//statement = "UPDATE userprofile SET friendRequests = friendRequests + 1 WHERE id = ?";
		//updateHelper(statement,params);
		
		return checkFriendRequest(fromUser,toUser);
	}
	/**
	 * Returns an List containing the usernames of the user's 
	 * 	received friend requests..
	 * @param name String
	 * @return List Contains strings
	 * @throws Exception if error
	 */
	
	public List<String> getPendingFriendsList(String name) throws Exception
	{
		String query = "SELECT name FROM users WHERE id IN "
				+ "(SELECT friendId FROM friends WHERE (id=? AND "
				+ "reqTo = ?) UNION SELECT id FROM "
				+ "friends WHERE (friendId=? AND reqTo = ?)) "
				+ "ORDER BY name";
		int id = getUserId(name);
		List<Integer> params = asList(id,id,id,id);
	
		List<HashMap<String,Object>> result= queryHelper(query,params);
		
		List<String> friendsList= new ArrayList<>();

		for (HashMap<String,Object> obj : result)
			friendsList.add((String)obj.get("name"));
		return friendsList;
	}
	/**
	 * Returns an List containing the usernames of the user's friends.
	 * @param name String
	 * @return List Contains strings
	 * @throws Exception if error
	 */
	
	public List<String> getFriendsList(String name) throws Exception
	{
		String query = "SELECT name FROM users WHERE id IN "
				+ "(SELECT friendId FROM friends WHERE (id=? AND "
				+ "reqFrom IS NULL) UNION SELECT id FROM "
				+ "friends WHERE (friendId=? AND reqFrom IS NULL)) "
				+ " and active=1 ORDER BY name";
		int id = getUserId(name);
		List<Integer> params = asList(id,id);
		
		List<HashMap<String,Object>> result= queryHelper(query,params);
		
		List<String> friendsList= new ArrayList<>();

		for (HashMap<String,Object> obj : result)
			friendsList.add((String)obj.get("name"));
		return friendsList;	
	}
	
	/**
	 * Creates a user record in the database, with the inputed name,password (and/or email) pair.
	 * Only creates a record if there isn't already a record with the same name.
	 * @param name String
	 * @param password String
	 * @param email String
	 * @return Boolean
	 * @throws Exception if error
	 */
	public Boolean createUser(String name, String password, String email) throws Exception
	{
		if (recordExists(name) || (name.length()>32)) {
			return false;
		}
		else {
			String statement = "INSERT into users (name, password, email) VALUES(?,?,?)";
		    List<String> params = asList(name, password, email);
		    updateHelper(statement, params);
		    System.out.println("User: " + name + " was created in db.");
		    
		    int userID = getUserId(name);
		    String statementDefaultProfile = "INSERT into userprofile (id, joined, lastSeen, realName, forumLvl, shares"
					+ ", likes, posts, friends, userDesc, avatar, currentGame) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			List<Object> params2 = asList(userID, java.sql.Date.valueOf(java.time.LocalDate.now()),
					java.sql.Date.valueOf(java.time.LocalDate.now()), "Anonymous", "Newbie", 0,
					0, 0, 0, "Insert Your user description here!", "images/UserAvatars/defaultAvatar.png",
					"Insert the current game you are playing here!");
			updateHelper(statementDefaultProfile, params2);
		
			String statementDefaultSocial = "INSERT into socialLinks (id, socialName, link) VALUES(?,?,?)";
			List<Object> params3 = asList(userID, "Insert a social site name here.", "Link here!");
			updateHelper(statementDefaultSocial, params3);
			
			String statementDefaultFavGame = "INSERT into favGames (id, gameName, gameLink) VALUES(?,?,?)";
			List<Object> params4 = asList(userID, "Insert a game name here.", "Link here!");
			updateHelper(statementDefaultFavGame, params4);
			
			String insertCurrentGames = "INSERT into currentGames (id, name) VALUES(?,?)";
			List<Object> params5 = asList(userID, "Pick Nexus supported game here");
			updateHelper(insertCurrentGames, params5);
			updateHelper(insertCurrentGames, params5);
			updateHelper(insertCurrentGames, params5);
			
			System.out.println("UserID:" + userID + ", Username:" + name + " default profile was created in db.");
		}
		return recordExists(name);
	}
	
	/**
	 * Creates a username with a null email.
	 * @param name String
	 * @param password String
	 * @return Boolean
	 * @throws Exception if error
	 */
	public Boolean createUser(String name, String password) throws Exception
	{
		return this.createUser(name, password, null);	
	}
	/**
	 * Checks if the user's account is set to active.
	 * @param name String
	 * @return Boolean
	 * @throws Exception if error
	 */
	
	public Boolean isActive(String name) throws Exception
	{
		String query = "SELECT active FROM users WHERE name = ?";
		List<String> params = asList(name);
		List<HashMap<String,Object>> results = queryHelper(query,params);
		if (results==null || results.size()==0)
				return false;
		else {
			return (int)((HashMap<String,Object>)results.get(0)).get("active")==1;
		}	
	}
	/**
	 * Deactivates a user's account.
	 * @param name String
	 * @return Boolean
	 * @throws Exception if error
	 */
	
	public Boolean deactivateUser(String name) throws Exception
	{
		String statement = "UPDATE USERS SET active = 0 WHERE name = ?";
		List<String> params = asList(name);
		updateHelper(statement,params);
		return !isActive(name);
	}
	
	/**
	 * Activates a user's account.
	 * @param name String
	 * @return Boolean
	 * @throws Exception if error
	 */
	
	public Boolean activateUser(String name) throws Exception
	{
		String statement = "UPDATE USERS SET active = 1 WHERE name = ?";
		List<String> params = asList(name);
		updateHelper(statement,params);
		return isActive(name); 
	}
	/**
	 * Checks if a record already exists in the database with the inputed name.
	 * @param name String
	 * @return Boolean
	 * @throws SQLException if error
	 */
	
	public Boolean recordExists (String name) throws SQLException
	{
		String query = "SELECT name FROM users WHERE name= ?";
		List<String> params = asList(name);
		List<HashMap<String,Object>> results = queryHelper(query,params);
		return (results==null)? null : (results.size() > 0);
	}

	/**
	 * Updates a user's password in the database with the inputed password string.
	 * @param name String
	 * @param password String
	 * @return Boolean
	 * @throws Exception if error
	 */
	
	public Boolean updatePassword(String name, String password) throws Exception
	{
		password = hashPassword(password);
		String statement = "UPDATE users SET password = ? WHERE name = ?";
		List<String> params = asList(password,name);
		updateHelper(statement,params);
		return retrievePassword(name).equals(password);		
	}
	
	/**
	 * Retrieves the inputed user's hashed password from the database.
	 * @param name String
	 * @return String
	 * @throws SQLException if error
	 */
	
	public String retrievePassword(String name) throws SQLException
	{
		String query = "SELECT password FROM users WHERE name= ?";
		List<String> params = asList(name);
		List<HashMap<String,Object>> results = queryHelper(query,params);
		if(results == null || results.size()==0)
			return null;
		else{
			return (String)(((HashMap<String,Object>)results.get(0)).get("password"));
		}
	}
	
	/**
	 * This method can get the users id from the users table in the database
	 * by the name of the user.
	 * @param name String
	 * @return int
	 * @throws Exception if error
	 */
	
	private int getUserId(String name) throws Exception
	{
		String query = "SELECT id FROM users WHERE name= ?";
		List<String> params = asList(name);
		List<HashMap<String,Object>> results = queryHelper(query,params);
		if(results == null || results.size() == 0)
			return -1;
		else 
			return (int)((HashMap<String,Object>)(results.get(0))).get("id");
	}
	
		/**
		 * This method updates a single wildcard column in the userProfile table.
		 * @param username String
		 * @param column String
		 * @param value String
		 * @return String
		 * @throws Exception if error
		 */
		public String updateUserProfile(String username, String column, String value) throws Exception
	{		
			int userID = getUserId(username);
			String update = "UPDATE userprofile SET " + column + "= ? WHERE id=?";
			List<Object> params = asList(value, userID);
			updateHelper(update, params);	
			
			// Same as above but
			// Need to query the database for the exact insert
			String query = "SELECT " + column +" FROM userprofile WHERE id= ?";
			List<Object> params2 = asList(userID);
			List<HashMap<String,Object>> results = queryHelper(query,params2);
			return results.get(0).get(column).toString();
		}
		
		/**
		 * This method edits tables that are one to many and return a list on query.
		 * The wildcards are tableName and 2 columns.
		 * @param username String
		 * @param tableName String
		 * @param column1 String
		 * @param column2 String
		 * @param names ArrayList String
		 * @param links ArrayList String 
		 * @return Profile Object
		 * @throws Exception if error
		 */
		public Profile updateUserProfileLists(String username, String tableName, String column1, String column2,
				ArrayList<String> names, ArrayList<String> links) throws Exception
		{
			Profile profile = new Profile();
			ArrayList<String> socialNames = new ArrayList<String>();
			ArrayList<String> socialLinks = new ArrayList<String>();
			
			
			int userID = getUserId(username);
			String delete = "DELETE FROM " + tableName + " WHERE id=?";
			List<Integer> paramsDelete = asList(userID);
			updateHelper(delete, paramsDelete);
			
			String insert = "INSERT INTO " + tableName + "(id," + column1 + "," + column2 + ") VALUES (?,?,?)";
			for (int i = 0; i < names.size(); i++) {
				List<Object> paramsInsert = asList(userID, names.get(i), links.get(i));
				updateHelper(insert, paramsInsert);
			}
			
			String query = "SELECT * FROM " + tableName + " WHERE id=?";
			List<Integer> paramsQuery = asList(userID);
			List<HashMap<String,Object>> results = queryHelper(query, paramsQuery);
			
			for (int i = 0; i < results.size(); i++) {
				socialNames.add(results.get(i).get(column1).toString());
				socialLinks.add(results.get(i).get(column2).toString());
			}
			profile.setSocialNames(socialNames);
			profile.setSocialLinks(socialLinks);
			return profile;
		}
		
		/**
		 * Method updates the currentGames table to save the correct order to return currentGames
		 * @param username String
		 * @param list String[]
		 * @throws Exception if error
		 */ 
		public void updateCurrentGames(String username, String[] list) throws Exception {
			String delete = "DELETE FROM currentGames WHERE id=?";
			String insert = "INSERT INTO currentGames (id,name) VALUES(?,?)";
			int id = getUserId(username);
			List<Integer> params = asList(id);
			updateHelper(delete, params);
			for (int i = 0; i < list.length; i++) {
				List<Object> params2 = asList(id, list[i]);
				updateHelper(insert, params2);
			}
		}
		
		/**
		 * Method removes entries from game tables to keep 3 games active only.
		 * @param username String
		 * @throws Exception username is not found
		 */
		public void clearCurrentGames(String username) throws Exception {
			String delete = "DELETE FROM csgo WHERE id=?";
			int id = getUserId(username);
			List<Integer> params = asList(id);
			updateHelper(delete, params);
			delete = "DELETE FROM hearthstone WHERE id=?";
			updateHelper(delete, params);
			delete = "DELETE FROM leagueoflegends WHERE id=?";
			updateHelper(delete, params);
			delete = "DELETE FROM worldofwarcraft WHERE id=?";
			updateHelper(delete, params);
			delete = "DELETE FROM diablo3 WHERE id=?";
			updateHelper(delete, params);
		}
		
		/**
		 * Method updates the worldofwarcraft table
		 * @param username String
		 * @param warcraftCharacter String
		 * @param warcraftRealm String
		 * @return JsonObject
		 * @throws Exception if error
		 */
		public JsonObject updateWOW(String username, String warcraftCharacter, String warcraftRealm) throws Exception {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("name", "World of Warcraft");
			String insert = "INSERT INTO worldofwarcraft (id,name,realm) VALUES(?,?,?) ON DUPLICATE KEY UPDATE name=?, realm=?"; 
			String query = "SELECT * FROM worldofwarcraft WHERE id=?";
			int id = getUserId(username);
			List<Object> params = asList(id,warcraftCharacter,warcraftRealm,warcraftCharacter,warcraftRealm);
			List<Integer> params2 = asList(id);
			updateHelper(insert, params);
			List<HashMap<String, Object>> result= queryHelper(query, params2);
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < result.size(); i++) {
				map.putAll(result.get(i));
			}
				jsonObject.addProperty("warcraftCharacter", (String) map.get("name"));
				jsonObject.addProperty("warcraftRealm", (String) map.get("realm"));
//				System.out.println((String) map.get("charname"));
//				System.out.println((String) map.get("realm"));
			return jsonObject;
		}
		
		/**
		 * Method updates the leagueoflegends table
		 * @param username String
		 * @param summoner String
		 * @return JsonObject
		 * @throws Exception if error
		 */
		public JsonObject updateLOL(String username, String summoner) throws Exception {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("name", "League of Legends");
			String insert = "INSERT INTO leagueoflegends (id,name) VALUES(?,?) ON DUPLICATE KEY UPDATE name=?"; 
			String query = "SELECT * FROM leagueoflegends WHERE id=?";
			int id = getUserId(username);
			List<Object> params = asList(id,summoner,summoner);
			List<Integer> params2 = asList(id);
			updateHelper(insert, params);
			List<HashMap<String, Object>> result= queryHelper(query, params2);
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < result.size(); i++) {
				map.putAll(result.get(i));
			}
				jsonObject.addProperty("summoner", (String) map.get("name"));
//				System.out.println((String) map.get("name"));
			return jsonObject;
		}
		
		/**
		 * Method updates the diablo3 table
		 * @param username String
		 * @param diabloCharacter String
		 * @return JsonObject
		 * @throws Exception if error
		 */
		public JsonObject updateDiablo3(String username, String diabloCharacter) throws Exception {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("name", "Diablo 3");
			String insert = "INSERT INTO diablo3 (id,name) VALUES(?,?) ON DUPLICATE KEY UPDATE name=?"; 
			String query = "SELECT * FROM diablo3 WHERE id=?";
			int id = getUserId(username);
			List<Object> params = asList(id,diabloCharacter,diabloCharacter);
			List<Integer> params2 = asList(id);
			updateHelper(insert, params);
			List<HashMap<String, Object>> result= queryHelper(query, params2);
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < result.size(); i++) {
				map.putAll(result.get(i));
			}
				jsonObject.addProperty("diabloCharacter", (String) map.get("name"));
//				System.out.println((String) map.get("name"));
			return jsonObject;
		}
		
//		/**
//		 * Method updates the hearthstone table
//		 * @param username String
//		 * @return JsonObject
//		 * @throws Exception if error
//		 */
		public JsonObject updateHearthStone(String username, String bnetname) throws Exception {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("name", "HearthStone");
			String insert = "INSERT INTO hearthstone (id,name) VALUES(?,?) ON DUPLICATE KEY UPDATE name=?"; 
			String query = "SELECT * FROM hearthstone WHERE id=?";
			int id = getUserId(username);
			List<Object> params = asList(id,bnetname,bnetname);
			List<Integer> params2 = asList(id);
			updateHelper(insert, params);
			List<HashMap<String, Object>> result= queryHelper(query, params2);
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < result.size(); i++) {
				map.putAll(result.get(i));
			}
				jsonObject.addProperty("bnetname", (String) map.get("name"));
//				System.out.println((String) map.get("name"));
			return jsonObject;
		}
		
		/**
		 * Method updates the csgo table
		 * @param username String
		 * @param csgoCharacter String
		 * @return JsonObject
		 * @throws Exception if error
		 */
		public JsonObject updateCSGO(String username, String csgoCharacter) throws Exception {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("name", "CSGO");
			String insert = "INSERT INTO csgo (id,name) VALUES(?,?) ON DUPLICATE KEY UPDATE name=?"; 
			String query = "SELECT * FROM csgo WHERE id=?";
			int id = getUserId(username);
			List<Object> params = asList(id,csgoCharacter,csgoCharacter);
			List<Integer> params2 = asList(id);
			updateHelper(insert, params);
			List<HashMap<String, Object>> result= queryHelper(query, params2);
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < result.size(); i++) {
				map.putAll(result.get(i));
			}
				jsonObject.addProperty("csgoCharacter", (String) map.get("name"));
//				System.out.println((String) map.get("name"));
			return jsonObject;
		}
	
	/**
	 * This method gets all the profile data from the database by user's name
	 * it is used for the first time the user loads their profile after logging in
	 * or switching back to their profile page from another page.
	 * @param username String
	 * @return Profile object
	 * @throws Exception if error
	 */
	public Profile getProfile(String username) throws Exception
	{
		Profile profile = new Profile();
		ArrayList<String> socialNames = new ArrayList<String>();
		ArrayList<String> socialLinks = new ArrayList<String>();
		ArrayList<String> favGameNames = new ArrayList<String>();
		ArrayList<String> favGameLinks = new ArrayList<String>();
		ArrayList<String> supportedGames = new ArrayList<String>();
		int userID = getUserId(username);
		String query = "SELECT * FROM userprofile WHERE id=?";
		List<Integer> params = asList(userID);
		List<HashMap<String, Object>> results = queryHelper(query, params);
		
		profile.setJoined((java.sql.Date) results.get(0).get("joined"));
		profile.setLastOnline((java.sql.Date) results.get(0).get("lastSeen"));
		profile.setRealName(results.get(0).get("realName").toString());
		profile.setRole(results.get(0).get("forumLvl").toString());
		profile.setShares((int) results.get(0).get("shares"));
		profile.setLikes((int) results.get(0).get("likes"));
		profile.setPosts((int) results.get(0).get("posts"));
		profile.setFriends((int) results.get(0).get("friends"));
		profile.setUserDesc(results.get(0).get("userDesc").toString());
		profile.setAvatar(results.get(0).get("avatar").toString());
		profile.setCurrentGame(results.get(0).get("currentGame").toString());
								
		query = "SELECT * FROM socialLinks WHERE id=?";
		results = queryHelper(query, params);
		for (int i = 0; i < results.size(); i++) {
			socialNames.add(results.get(i).get("socialName").toString());
			socialLinks.add(results.get(i).get("link").toString());
		}
		profile.setSocialNames(socialNames);
		profile.setSocialLinks(socialLinks);
		
		query = "SELECT * FROM favGames WHERE id=?";
		results = queryHelper(query, params);
		for (int i = 0; i < results.size(); i++) {
			favGameNames.add(results.get(i).get("gameName").toString());
			favGameLinks.add(results.get(i).get("gameLink").toString());
		}
		profile.setFavGameNames(favGameNames);
		profile.setFavGameLinks(favGameLinks);
		
		query = "SELECT * FROM currentGames WHERE id=?";
		results = queryHelper(query, params);
		for (int i = 0; i < results.size(); i++) {
			supportedGames.add(results.get(i).get("name").toString());
		}
		profile.setSupportedGames(supportedGames);
				
		return profile;
	}
	
	/**
	 * Method queries and returns the currentGames table information for the user
	 * @param username String
	 * @return JsonArray
	 * @throws Exception if error
	 */
	@SuppressWarnings("unused")
	public JsonArray getCurrentGames(String username) throws Exception {
		JsonArray jsonArray = new JsonArray();
		String query = "SELECT * FROM currentGames WHERE id=?";
		int id = getUserId(username);
		List<Integer> params = asList(id);
		List<HashMap<String, Object>> result= queryHelper(query, params);
		ArrayList<Object> array = new ArrayList<Object>();
		for (int i = 0; i < result.size(); i++) {
			JsonObject jsonObject = new JsonObject();
			String gameName = (String) result.get(i).get("name");			
			if (gameName.equals("World of Warcraft")) {
				String queryWOW = "SELECT * FROM worldofwarcraft WHERE id=?";
				List<Integer> paramsWOW = asList(id);
				List<HashMap<String, Object>> resultWOW = queryHelper(queryWOW, paramsWOW);
				HashMap<String, Object> mapWOW = new HashMap<String, Object>();
				for (int j = 0; j < resultWOW.size(); j++) {
					mapWOW.putAll(resultWOW.get(j));
				}
				jsonObject.addProperty("name", "World of Warcraft");
				jsonObject.addProperty("warcraftCharacter", (String) mapWOW.get("name"));
				jsonObject.addProperty("warcraftRealm", (String) mapWOW.get("realm"));
				jsonArray.add(jsonObject);
			}
			else if (gameName.equals("League of Legends")) {
				String queryLOL = "SELECT * FROM leagueoflegends WHERE id=?";
				List<Integer> paramsLOL = asList(id);
				List<HashMap<String, Object>> resultLOL = queryHelper(queryLOL, paramsLOL);
				HashMap<String, Object> mapLOL = new HashMap<String, Object>();
				mapLOL.putAll(resultLOL.get(0));
				jsonObject.addProperty("name", "League of Legends");
				jsonObject.addProperty("summoner", (String) mapLOL.get("name"));
				jsonArray.add(jsonObject);
			}
			else if (gameName.equals("CSGO")) {
				String queryCSGO = "SELECT * FROM csgo WHERE id=?";
				List<Integer> paramsCSGO = asList(id);
				List<HashMap<String, Object>> resultCSGO = queryHelper(queryCSGO, paramsCSGO);
				HashMap<String, Object> mapCSGO = new HashMap<String, Object>();
				mapCSGO.putAll(resultCSGO.get(0));
				jsonObject.addProperty("name", "CS:GO");
				jsonObject.addProperty("charname", (String) mapCSGO.get("name"));
				jsonArray.add(jsonObject);
			}
			else if (gameName.equals("HearthStone")) { 
				String queryHS = "SELECT * FROM hearthstone WHERE id=?";
				List<Integer> paramsHS = asList(id);
				List<HashMap<String, Object>> resultHS = queryHelper(queryHS, paramsHS);
				HashMap<String, Object> mapHS = new HashMap<String, Object>();
				mapHS.putAll(resultHS.get(0));
				jsonObject.addProperty("name", "HearthStone");
				jsonObject.addProperty("bnetname", (String) mapHS.get("name"));
				jsonArray.add(jsonObject);
			}
			else if (gameName.equals("Diablo 3")) {
				String queryD3 = "SELECT * FROM diablo3 WHERE id=?";
				List<Integer> paramsD3 = asList(id);
				List<HashMap<String, Object>> resultD3 = queryHelper(queryD3, paramsD3);
				HashMap<String, Object> mapD3 = new HashMap<String, Object>();
				mapD3.putAll(resultD3.get(0));
				jsonObject.addProperty("name", "Diablo 3");
				jsonObject.addProperty("diabloCharacter", (String) mapD3.get("name"));
				jsonArray.add(jsonObject);
			}
			else { // Default
				jsonObject.addProperty("name", "default");
				jsonArray.add(jsonObject);
			}
		}
		return jsonArray;
	}
	
	/**
	 * Method that returns all the intervals that a friend and user for matchFinding.
	 * @param username String
	 * @return JsonArray of JsonObjects
	 * @throws Exception If username does not exist
	 */
	public JsonArray getMatchFinderResults(String username) throws Exception {
		JsonArray jsonArray = new JsonArray();
		int id = getUserId(username);
		List<String> friends = getFriendsList(username);
		System.out.println(friends);

		for(String friend : friends) {
			int friendId = getUserId(friend);
			String query = "SELECT A.id, A.start, A.end"
					+" FROM matchFinder A, matchFinder B"
					+" WHERE (A.id=? AND B.id=? AND A.start < B.end)"
					+" AND (A.end > B.start)";
			List<Integer> params = asList(friendId, id);
			List<HashMap<String, Object>> result = queryHelper(query, params);
			if (result != null) {
				for (HashMap<String, Object> obj : result) {
					JsonObject jsonObj = new JsonObject();
					jsonObj.addProperty("FriendName", friend);						
					jsonObj.addProperty("start", obj.get("start").toString());
					jsonObj.addProperty("end", obj.get("end").toString());
					jsonArray.add(jsonObj);
				}
			}	
		}

		return jsonArray;
	}
	
	/**
	 * Takes in a database statement and a list of PreparedStatement
	 * 	 parameters for the statement
	 * @param statement String
	 * @param params List&lt;Object&gt;
	 * @return int -1 if error, 0 if update failed, 1 if updated succeeded.
	 * @throws SQLException if error
	 */
	private int updateHelper(String statement, List<?> params) throws SQLException
	{
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
		try
		{
			pstmt = conn.prepareStatement(statement);
			int i = 1;
			for(Object obj: params)
			{
				if (obj instanceof String)
					pstmt.setString(i++, (String) obj);					
				else if (obj instanceof Integer)
					pstmt.setInt(i++, (int) obj);
				else if (obj instanceof java.sql.Date)
					pstmt.setDate(i++, (java.sql.Date) obj);
				else return -1;    /* If you need something other than setString, setInt or setDate
				 					    add it to another else if, following this form*/
			}
			System.out.println(pstmt);
			return pstmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return -1;
		}
	}
	/**
	 * Takes in a database query and a list of PreparedStatement
	 * 	 parameters for the query.
	 * @param query String
	 * @param params List&lt;Object&gt;
	 * @return List&lt;HashMap&gt; Returned database table.
	 * @throws SQLException if error
	 */
	private List<HashMap<String,Object>> queryHelper(String query, List<?> params) throws SQLException
	{
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
		try 
		{
			pstmt = conn.prepareStatement(query);
			int i = 1;
			for (Object obj: params)
			{
				if (obj instanceof String)
					pstmt.setString(i++, (String) obj);					
				else if (obj instanceof Integer)
					pstmt.setInt(i++, (int) obj);
				else if (obj instanceof java.sql.Date)
					pstmt.setDate(i++, (java.sql.Date) obj);
				else return null;    /* If you need something other than setString or setInt
				 					    add it to another else if, following the form*/
			}
			ResultSet result = pstmt.executeQuery();
			System.out.println(pstmt);
			ResultSetMetaData md = result.getMetaData();
			int colCount = md.getColumnCount();
			List<HashMap<String,Object>> resultList = new ArrayList<HashMap<String,Object>>();
			while(result.next()){
				HashMap<String,Object> row = new HashMap<String,Object>(colCount);
				for(i = 1; i<=colCount; i++) {
					row.put(md.getColumnName(i), result.getObject(i));
				}
				resultList.add(row);
			}
			return resultList;		
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		finally {
			if (pstmt!=null) pstmt.close();
			conn.close();		
		}
	}
	/**
	 * Connect to the DB and do some stuff
	 * @param args standard main args
	 * @throws Exception if error
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception
	{
		NexusDB app = new NexusDB();
	}
}