package com.nexus;

import java.sql.*;
//import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;

/**
 * This class contains the database methods.
 * @author David Kopp
 *
 */
@SuppressWarnings("all")
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
		List params = asList(id1,id2);
		List result = queryHelper(query, params);
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
		
		List params = asList(id1,id2);
		List result = queryHelper(query, params);
		return (result==null)? null : (result.size()>0);

	}
	/**
	 * Updates two users' friend status from pending to friends.
	 * @param name1 String
	 * @param name2 String
	 * @return Boolean
	 * @throws SQLException if error
	 */
	
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
		List params = asList(id1,id2);
		updateHelper(statement,params);
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
	
	public Boolean deleteFriend(String name1,String name2) throws Exception
	{
		int id1= getUserId(name1);
		int id2= getUserId(name2);
		if(id1 == -1 || id2 == -1)	
			return null;
		if (id1 > id2)
		{
			int tmp = id1;
			id1 = id2;
			id2 = tmp;
		}
		String statement = "DELETE FROM friends WHERE id=? AND friendId=?";
		List params = asList(id1,id2);
		updateHelper(statement,params);
		return !checkFriend(name1,name2);
	}

	/**
	 * Updates the database to indicate a pending friend request from one user to another.
	 * @param fromUser String
	 * @param toUser String
	 * @return Boolean
	 * @throws SQLException if error
	 */
	
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
		List params = asList(lowerId,higherId,fromId,toId);
		updateHelper(statement, params);
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
		List params = asList(id,id,id,id);
	
		List result= queryHelper(query,params);
		
		int i = 0;
		for (HashMap obj: (ArrayList<HashMap>)result)
				result.set(i++, obj.get("name"));
		return result;
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
		List params = asList(id,id);
		List result= queryHelper(query,params);
		int i = 0;
		for (HashMap obj: (ArrayList<HashMap>)result)
				result.set(i++, obj.get("name"));
		return result;		
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

		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
		try {
			if (recordExists(name) || (name.length()>32)) 
				return false; //already exists
			else {
		     String statement = "INSERT into users (name, password, email) VALUES(?,?,?)";
		     pstmt = conn.prepareStatement(statement);
		     pstmt.setString(1, name);
		     pstmt.setString(2, password);
		     pstmt.setString(3, email);
		     pstmt.executeUpdate(); 
		     
		     System.out.println(pstmt);
		     //return recordExists(name);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		int userID = getUserId(name);
		try {
			String statement = "INSERT into userprofile (id, joined, lastSeen, realName, forumLvl, shares"
					+ ", likes, posts, friends, userDesc, avatar, currentGame) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(statement);
			pstmt.setInt(1, userID);
			pstmt.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
			pstmt.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
			pstmt.setString(4, "Anonymous");
			pstmt.setString(5, "Newbie");
			pstmt.setInt(6, 0);
			pstmt.setInt(7, 0);
			pstmt.setInt(8, 0);
			pstmt.setInt(9, 0);
			pstmt.setString(10, "Insert your user description here!");
			pstmt.setString(11, "images/UserAvatars/defaultAvatar.png");
			pstmt.setString(12, "Insert the current game your are playing here!");
			pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		try {
			String statement = "INSERT into socialLinks (id, socialName, link) VALUES(?,?,?)";
			pstmt = conn.prepareStatement(statement);
			pstmt.setInt(1, userID);
			pstmt.setString(2, "Insert a social sites name here.");
			pstmt.setString(3, "Link here");
			pstmt.executeUpdate();
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		try { // rework
			String statement = "INSERT into favGames (id, gameName, gameLink) VALUES(?,?,?)";
			pstmt = conn.prepareStatement(statement);
			pstmt.setInt(1, userID);
			pstmt.setString(2, "Insert a game name here.");
			pstmt.setString(3, "Link here");
			pstmt.executeUpdate();
		}
		catch(Exception e) { // rework
			e.printStackTrace();
			return null;
		}
//		try { // rework
//			String statement = "INSERT into gamesSupported (id, gameName) VALUES(?,?)";
//			pstmt = conn.prepareStatement(statement);
//			pstmt.setInt(1, userID);
//			pstmt.setString(2, "Insert Nexus supported game name here.");
//			pstmt.executeUpdate();
//		}
//		catch(Exception e) { // rework
//			e.printStackTrace();
//			return null;
//		}
		finally {
			if (pstmt!=null) pstmt.close();
			conn.close();
		}
		String insertCurrentGames = "INSERT into currentGames (id, name) VALUES(?,?)";
		List params = asList(userID,"Pick Nexus supported game here");
		updateHelper(insertCurrentGames, params);
		updateHelper(insertCurrentGames, params);
		updateHelper(insertCurrentGames, params);
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
		List params = asList(name);
		List results = queryHelper(query,params);
		if (results==null || results.size()==0)
				return null;
		else {
			return (int)((HashMap)results.get(0)).get("active")==1;
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
		List params = asList(name);
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
		List params = asList(name);
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
		List params = asList(name);
		List results = queryHelper(query,params);
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
		List params = asList(password,name);
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
		List params = asList(name);
		List results = queryHelper(query,params);
		if(results == null || results.size()==0)
			return null;
		else{
			return (String)(((HashMap)results.get(0)).get("password"));
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
		List params = asList(name);
		List results = queryHelper(query,params);
		if(results == null || results.size() == 0)
			return -1;
		else 
			return (int)((HashMap)(results.get(0))).get("id");
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
			//creates connection b/w front and database
			Connection connection = this.getConnection();
			PreparedStatement pstmt = null;
			String result = null;
			
			
			int userID = getUserId(username);
			String update = "UPDATE userprofile SET " + column + "= ? WHERE id=?";
			
			try
			{
				//preparing a statment that the update will run
				pstmt = connection.prepareStatement(update);
				//these are holders for the String update
				pstmt.setString(1, value);
				pstmt.setInt(2, userID);
					System.out.println(pstmt);
				//preparing an execution for update
				pstmt.executeUpdate();	
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}	
			
			//Same as above but
			//Need to query the database for the exact insert
			String query = "SELECT " + column +" FROM userprofile WHERE id= ?";
			try
			{
				pstmt = connection.prepareStatement(query);
				pstmt.setInt(1, userID);
				//getting query results and puts it in results
				ResultSet results = pstmt.executeQuery();
					System.out.println(pstmt);
				if (results.next())
				{	
					result = results.getString(column);
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				return null;
			}
			finally 
			{
				if(pstmt != null)
				{
					pstmt.close();
				}
				connection.close();
			}
			return result;
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
			Connection connection = this.getConnection();
			PreparedStatement pstmt = null;
			Profile profile = null;
			ArrayList<String> socialNames = new ArrayList<String>();
			ArrayList<String> socialLinks = new ArrayList<String>();
			
			
			int userID = getUserId(username);
			String delete = "DELETE FROM " + tableName + " WHERE id=?";
			String insert = "INSERT INTO " + tableName + "(id," + column1 + "," + column2 + ") VALUES (?,?,?)";
			
			try
			{
				pstmt = connection.prepareStatement(delete);
				pstmt.setInt(1, userID);
					System.out.println(pstmt);
				pstmt.executeUpdate();		
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
			try
			{
				//preparing a statment that the update will run
				pstmt = connection.prepareStatement(insert);
				//these are holders for the String update
				for (int i = 0; i < names.size(); i++)
				{
					pstmt.setInt(1, userID);
					pstmt.setString(2, names.get(i));
					pstmt.setString(3, links.get(i));
					pstmt.executeUpdate();
					System.out.println(pstmt);
				}	
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}	
			
			String query = "SELECT * FROM " + tableName + " WHERE id=" +userID;
			try {
				profile = new Profile();
				pstmt = connection.prepareStatement(query);
				ResultSet results = pstmt.executeQuery();
					System.out.println(pstmt);
				while (results.next())
				{
					socialNames.add(results.getString(column1));
					socialLinks.add(results.getString(column2));
				}
				profile.setSocialNames(socialNames);
				profile.setSocialLinks(socialLinks);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			finally 
			{
				if(pstmt != null)
				{
					pstmt.close();
				}
				connection.close();
			}
			return profile;
		}
		
		/**
		 * This method updates a table that has only one column and is a one to many relationship.
		 * Wildcards are tableName and column.
		 * @param username String
		 * @param tableName String
		 * @param column1 String
		 * @param names ArrayList String
		 * @return ArrayList String
		 * @throws Exception if error
		 */
		public ArrayList<String> updateUserProfileList(String username, String tableName, String column1, 
				ArrayList<String> names) throws Exception
		{
			Connection connection = this.getConnection();
			PreparedStatement pstmt = null;
			ArrayList<String> gameNames = new ArrayList<String>();
			
			
			int userID = getUserId(username);
			String delete = "DELETE FROM " + tableName + " WHERE id=?";
			String insert = "INSERT INTO " + tableName + "(id," + column1 + ") VALUES (?,?)";
			
			try
			{
				pstmt = connection.prepareStatement(delete);
				pstmt.setInt(1, userID);
					System.out.println(pstmt);
				pstmt.executeUpdate();		
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}
			try
			{
				//preparing a statment that the update will run
				pstmt = connection.prepareStatement(insert);
				//these are holders for the String update
				for (int i = 0; i < names.size(); i++)
				{
					pstmt.setInt(1, userID);
					pstmt.setString(2, names.get(i));
					pstmt.executeUpdate();
					System.out.println(pstmt);
				}	
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return null;
			}	
			
			String query = "SELECT * FROM " + tableName + " WHERE id=" +userID;
			try {
				pstmt = connection.prepareStatement(query);
				ResultSet results = pstmt.executeQuery();
					System.out.println(pstmt);
				while (results.next())
				{
					gameNames.add(results.getString(column1));
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			finally 
			{
				if(pstmt != null)
				{
					pstmt.close();
				}
				connection.close();
			}
			return gameNames;
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
			List params = asList(id);
			updateHelper(delete, params);
			for (int i = 0; i < list.length; i++) {
				List params2 = asList(id, list[i]);
				updateHelper(insert, params2);
			}
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
			List params = asList(id,warcraftCharacter,warcraftRealm,warcraftCharacter,warcraftRealm);
			List params2 = asList(id);
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
			List params = asList(id,summoner,summoner);
			List params2 = asList(id);
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
		 * Method updates the csgo table
		 * @param username String
		 * @return JsonObject
		 * @throws Exception if error
		 */
		public JsonObject updateCSGO(String username) throws Exception {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("name", "CS:GO");
			String insert = "INSERT INTO csgo (id,name) VALUES(?,?) ON DUPLICATE KEY UPDATE name=?"; 
			String query = "SELECT * FROM csgo WHERE id=?";
			int id = getUserId(username);
			List params = asList(id,"null","null");
			List params2 = asList(id);
			updateHelper(insert, params);
			List<HashMap<String, Object>> result= queryHelper(query, params2);
			HashMap<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < result.size(); i++) {
				map.putAll(result.get(i));
			}
				jsonObject.addProperty("charname", (String) map.get("name"));
//				System.out.println((String) map.get("name"));
			return jsonObject;
		}
		
		/**
		 * Method updates the hearthstone table
		 * @param username String
		 * @return JsonObject
		 * @throws Exception if error
		 */
		public JsonObject updateHearthStone(String username) throws Exception {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("name", "HearthStone");
			String insert = "INSERT INTO hearthstone (id,name) VALUES(?,?) ON DUPLICATE KEY UPDATE name=?"; 
			String query = "SELECT * FROM hearthstone WHERE id=?";
			int id = getUserId(username);
			List params = asList(id,"null","null");
			List params2 = asList(id);
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
	 * This method gets all the profile data from the database by user's name
	 * it is used for the first time the user loads their profile after logging in
	 * or switching back to their profile page from another page.
	 * @param username String
	 * @return Profile object
	 * @throws Exception if error
	 */
	public Profile getProfile(String username) throws Exception
	{
		Connection connection = this.getConnection();
		PreparedStatement pstmt = null;
		Profile profile = null;
		int userID = getUserId(username);
		String query = "SELECT * FROM userprofile WHERE id=" +userID;
		try
		{
			pstmt = connection.prepareStatement(query);
			ResultSet results = pstmt.executeQuery();
				System.out.println(pstmt);
			if (results.next())
			{
				profile = new Profile();
				profile.setJoined(results.getDate("joined"));
				profile.setLastOnline(results.getDate("lastSeen"));
				profile.setRealName(results.getString("realName"));
				profile.setRole(results.getString("forumLvl"));
				profile.setShares(results.getInt("shares"));
				profile.setLikes(results.getInt("likes"));
				profile.setPosts(results.getInt("posts"));
				profile.setFriends(results.getInt("friends"));
				profile.setUserDesc(results.getString("userDesc"));
				profile.setAvatar(results.getString("avatar"));
				profile.setCurrentGame(results.getString("currentGame"));
				
				ArrayList<String> socialNames = new ArrayList<String>();
				ArrayList<String> socialLinks = new ArrayList<String>();
				ArrayList<String> favGameNames = new ArrayList<String>();
				ArrayList<String> favGameLinks = new ArrayList<String>();
				ArrayList<String> supportedGames = new ArrayList<String>();
				query = "SELECT * FROM socialLinks WHERE id=" +userID;
				try {
					pstmt = connection.prepareStatement(query);
					ResultSet results2 = pstmt.executeQuery();
						System.out.println(pstmt);
					while (results2.next())
					{
						socialNames.add(results2.getString("socialName"));
						socialLinks.add(results2.getString("link"));
					}
					profile.setSocialNames(socialNames);
					profile.setSocialLinks(socialLinks);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				query = "SELECT * FROM favGames WHERE id=" +userID;
				try {
					pstmt = connection.prepareStatement(query);
					ResultSet results2 = pstmt.executeQuery();
						System.out.println(pstmt);
					while (results2.next())
					{
						favGameNames.add(results2.getString("gameName"));
						favGameLinks.add(results2.getString("gameLink"));
					}
					profile.setFavGameNames(favGameNames);
					profile.setFavGameLinks(favGameLinks);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				query = "SELECT * FROM currentGames WHERE id=" +userID;
				try {
					pstmt = connection.prepareStatement(query);
					ResultSet results3 = pstmt.executeQuery();
						System.out.println(pstmt);
					while(results3.next())
					{
						supportedGames.add(results3.getString("name"));
					}
					profile.setSupportedGames(supportedGames);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if(pstmt != null)
			{
				pstmt.close();
			}
			connection.close();
		}
		return profile;
	}
	
	/**
	 * Method queries and returns the currentGames table information for the user
	 * @param username String
	 * @return JsonArray
	 * @throws Exception if error
	 */
	public JsonArray getCurrentGames(String username) throws Exception {
		JsonArray jsonArray = new JsonArray();
		String query = "SELECT * FROM currentGames WHERE id=?";
		int id = getUserId(username);
		List params = asList(id);
		List<HashMap<String, Object>> result= queryHelper(query, params);
		ArrayList<Object> array = new ArrayList<Object>();
		for (int i = 0; i < result.size(); i++) {
			JsonObject jsonObject = new JsonObject();
			String gameName = (String) result.get(i).get("name");			
			if (gameName.equals("World of Warcraft")) {
				String queryWOW = "SELECT * FROM worldofwarcraft WHERE id=?";
				List paramsWOW = asList(id);
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
				List paramsLOL = asList(id);
				List<HashMap<String, Object>> resultLOL = queryHelper(queryLOL, paramsLOL);
				HashMap<String, Object> mapLOL = new HashMap<String, Object>();
				mapLOL.putAll(resultLOL.get(0));
				jsonObject.addProperty("name", "League of Legends");
				jsonObject.addProperty("summoner", (String) mapLOL.get("name"));
				jsonArray.add(jsonObject);
			}
			else if (gameName.equals("CSGO")) {
				String queryCSGO = "SELECT * FROM csgo WHERE id=?";
				List paramsCSGO = asList(id);
				List<HashMap<String, Object>> resultCSGO = queryHelper(queryCSGO, paramsCSGO);
				HashMap<String, Object> mapCSGO = new HashMap<String, Object>();
				mapCSGO.putAll(resultCSGO.get(0));
				jsonObject.addProperty("name", "CS:GO");
				jsonObject.addProperty("charname", (String) mapCSGO.get("name"));
				jsonArray.add(jsonObject);
			}
			else if (gameName.equals("HearthStone")) { 
				String queryHS = "SELECT * FROM hearthstone WHERE id=?";
				List paramsHS = asList(id);
				List<HashMap<String, Object>> resultHS = queryHelper(queryHS, paramsHS);
				HashMap<String, Object> mapHS = new HashMap<String, Object>();
				mapHS.putAll(resultHS.get(0));
				jsonObject.addProperty("name", "HearthStone");
				jsonObject.addProperty("bnetname", (String) mapHS.get("name"));
				jsonArray.add(jsonObject);
			}
			else { // Default
				String queryCG = "SELECT * FROM currentGames WHERE id=?";
				List paramsCG = asList(id);
				List<HashMap<String, Object>> resultCG = queryHelper(queryCG, paramsCG);
				for (int j = 0; j < resultCG.size(); j++) {
					jsonObject.addProperty("name", (String) resultCG.get(j).get("name"));
				}
				jsonArray.add(jsonObject);
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
	private int updateHelper(String statement, List<Object> params) throws SQLException
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
				else return -1;    /* If you need something other than setString or setInt
				 					    add it to another else if, following the form*/
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
	private List<HashMap<String,Object>> queryHelper(String query, List<Object> params) throws SQLException
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
	public static void main(String[] args) throws Exception
	{
		NexusDB app = new NexusDB();
	}
}