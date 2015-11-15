package com.nexus;
import java.sql.Connection;
//import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * This class is the databse methods.
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
		try {
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
	private Connection getConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + this.dbName,
				connectionProps);
		return conn;
	}
	/**
	 * Checks if two users are friends.
	 * @param name1 String
	 * @param name2 String
	 * @return Boolean
	 * @throws SQLException
	 */
	public Boolean checkFriend(String name1, String name2) throws SQLException{
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
		try {
			int id1= getUserId(name1);
			int id2= getUserId(name2);
			if (id1 > id2){
				int tmp = id1;
				id1 = id2;
				id2 = tmp;
			}
			String query = "SELECT id,friendId FROM friends WHERE id=? AND "
					+ "friendId=? AND reqFrom IS NULL AND reqTo IS NULL";
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, id1);
			pstmt.setInt(2, id2);
			ResultSet result = pstmt.executeQuery();
			return result.next();
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
		finally {
			if (pstmt!=null) pstmt.close();
			conn.close();
		}
	}
	/**
	 * Updates two users' friend status from pending to friends.
	 * @param name1 String
	 * @param name2 String
	 * @return Boolean
	 * @throws SQLException
	 */
	public Boolean updateFriendStatus(String name1, String name2) throws SQLException{
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
		try {
			int id1= getUserId(name1);
			int id2= getUserId(name2);
			if (id1 > id2){
				int tmp = id1;
				id1 = id2;
				id2 = tmp;
			}
			String command = "UPDATE friends SET reqFrom=NULL,reqTo=NULL where id=? AND friendId=?";
			pstmt= conn.prepareStatement(command);
			pstmt.setInt(1, id1);
			pstmt.setInt(2, id2);
			pstmt.executeUpdate();
			return true;
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
	 * Deletes a friend relationship between two users.
	 * @param name1 String
	 * @param name2 String
	 * @return Boolean
	 * @throws SQLException
	 */
	public Boolean deleteFriend(String name1,String name2) throws SQLException{
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
		try {
			int id1= getUserId(name1);
			int id2= getUserId(name2);
			if (id1 > id2){
				int tmp = id1;
				id1 = id2;
				id2 = tmp;
			}
			String command = "DELETE FROM friends WHERE id=? AND friendId=?";
			pstmt = conn.prepareStatement(command);
			pstmt.setInt(1, id1);
			pstmt.setInt(2, id2);
			pstmt.executeUpdate();
			return !checkFriend(name1,name2);
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
		finally {
			if (pstmt!=null) pstmt.close();
			conn.close();
		}
	}
	/**
	 * Updates the database to indicate a pending friend request from one user to another.
	 * @param fromUser String
	 * @param toUser String
	 * @return Boolean
	 * @throws SQLException
	 */
	public Boolean  createFriendRequest(String fromUser, String toUser) throws SQLException{
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
		try {
			
			String command = "INSERT into friends VALUES(?,?,?,?)";
			pstmt=conn.prepareStatement(command);
			int fromId = getUserId(fromUser);
			int toId = getUserId(toUser);
			int lowerId,higherId;
			if (fromId<toId){
				lowerId=fromId;
				higherId=toId;
			}
			else {
				lowerId=toId;
				higherId=fromId;
			}
			pstmt.setInt(1, lowerId);
			pstmt.setInt(2, higherId);
			pstmt.setInt(3, fromId);
			pstmt.setInt(4, toId);


			pstmt.executeUpdate();
			System.out.println(pstmt);
			return true;
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			
			if (pstmt!=null) pstmt.close();
			conn.close();

		}	
	}
	/**
	 * Returns an ArrayList containing the usernames of the user's friends.
	 * @param name String
	 * @return ArrayList<String>
	 * @throws Exception
	 */
	
	public ArrayList<String> getFriendsList(String name) throws Exception{
		long startTime=System.currentTimeMillis();
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
		try {
			String query = "select name from users where id in "
				+ "(select friendId from friends where id=? "
				+ "union select id from friends where friendId=?)";
		pstmt = conn.prepareStatement(query);
		int id = getUserId(name);
		pstmt.setInt(1, id);
		pstmt.setInt(2, id);
		ResultSet result = pstmt.executeQuery();
		System.out.println(pstmt);
		ArrayList<String> friends = new ArrayList<String>();
		while(result.next())
			friends.add(result.getString(1));
 
		return friends;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		finally {
			if (pstmt!=null) pstmt.close();
			conn.close();
			long endTime=System.currentTimeMillis();
			System.out.println(endTime-startTime);
		}
		
	}
	
	/**
	 * Delete's a user from the database with the inputed name
	 * @param name String
	 * @return Boolean
	 * @throws SQLException if error
	 */
	public Boolean deleteUser(String name) throws SQLException {

		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
		try {
			
			String command = "DELETE from users WHERE name = ?";
			pstmt=conn.prepareStatement(command);
			pstmt.setString(1, name);
			pstmt.executeUpdate();
			System.out.println(pstmt);
			return !recordExists(name);
			
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
	 *Creates a user record in the database, with the inputed name,password (and/or email) pair.
	 *Only creates a record if there isn't already a record with the same name.
	 * Creates a user record in the database, with the inputed name,password pair.
	 * Only creates a record if there isn't already a record with the same name.
	 * @param name String
	 * @param password String
	 * @return Boolean
	 * @throws Exception if error
	 */
	public Boolean createUser(String name, String password, String email) throws Exception {

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
		     return true;
			}
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
	public Boolean createUser (String name, String password) throws Exception{
		return this.createUser(name, password,null);	
	}
	
	/**
	 * Checks if a record already exists in the database with the inputed name.
	 * @param name String
	 * @return Boolean
	 * @throws SQLException if error
	 */
	public Boolean recordExists (String name) throws SQLException{
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
			try {
				String query = "SELECT name FROM users WHERE name= ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, name);
				ResultSet results = pstmt.executeQuery();

				if (results.next())
					return true;
				else return false;
			} catch (Exception e) {
				
				e.printStackTrace();
				return null;
			}
			finally {
				if (pstmt!=null) pstmt.close();
				conn.close();
			}
		
	}

	/**
	 * Updates a user's password in the database with the inputed password string.
	 * @param name String
	 * @param password String
	 * @throws Exception if error
	 */
	public Boolean updatePassword(String name, String password) throws Exception{
		password = hashPassword(password);
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
		try {
			
			String statement = "UPDATE users SET password = ? WHERE name = ?";
			pstmt=conn.prepareStatement(statement);
			pstmt.setString(1, password);
			pstmt.setString(2, name);
			
					System.out.println(pstmt);
			pstmt.executeUpdate();
			return true;
		}
		catch (Exception e){
			e.printStackTrace();
			return retrievePassword(name).equals(password);			
		}
		finally {
			if (pstmt!=null) pstmt.close();
			conn.close();
		}
		
	}
	
	/**
	 * Retrieves the inputed user's hashed password from the database.
	 * @param name String
	 * @return String
	 * @throws Exception if error
	 */
	public String retrievePassword(String name) throws SQLException{
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
			try {
				String query = "SELECT password FROM users WHERE name= ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, name);
				ResultSet results = pstmt.executeQuery();
				
						System.out.println(pstmt);
				
				if (results.next())
					return results.getString("password");
				else return "";
			} catch (Exception e) {
				e.printStackTrace();
				return "";
			}
			finally {
				if (pstmt!=null) pstmt.close();
				conn.close();
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
		Connection connection = this.getConnection();
		PreparedStatement pstmt = null;
		String query = "SELECT id FROM users WHERE name= ?";
		try
		{
			pstmt = connection.prepareStatement(query);	
			pstmt.setString(1, name);
			ResultSet results = pstmt.executeQuery();
				System.out.println(pstmt);
			if (results.next())
			{	
				return results.getInt("id");
			}
			else
			{
				return -1;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return -1;
		}
		finally 
		{
			if(pstmt != null)
			{
				pstmt.close();
			}
			connection.close();
		}
	}
	
	//Inserting realName
		//Call an insert, call a query
		public Profile insertRealName(String username, String realName) throws Exception {
			//creates connection b/w front and database
			Connection connection = this.getConnection();
			PreparedStatement pstmt = null;
			Profile profile = null;
			
			
			int userID = getUserId(username);
			//updates the database with frontend username information
			String update = "UPDATE userprofile SET realName= ? WHERE id=?";
			
			try
			{
				//preparing a statment that the update will run
				pstmt = connection.prepareStatement(update);
				//these are holders for the String update
				pstmt.setString(1, realName);
				pstmt.setInt(2, userID);
					System.out.println(pstmt);
				//preparing an execution for update
				pstmt.executeUpdate();	
			}
			finally
			{
				//if the preparation doesn't equal null, then close it
				if(pstmt != null)
				{
					pstmt.close();
				}
			}	
			
			//Same as above but
			//Need to query the database for the exact insert
			String query = "SELECT realName FROM userprofile WHERE id= ?";
			try
			{
				pstmt = connection.prepareStatement(query);	
				pstmt.setInt(1, userID);
				//getting query results and puts it in results
				ResultSet results = pstmt.executeQuery();
					System.out.println(pstmt);
				//so if results has a row (or next) then is true and sets the realName to profile obj
				if (results.next())
				{	
					//taking profile object using the setter for the real name
					profile = new Profile();
					profile.setRealName(results.getString("realName"));
				}
				return profile;
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				return profile;
			}
			finally 
			{
				if(pstmt != null)
				{
					pstmt.close();
				}
				connection.close();
			}
			
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
				profile.setFollowers(results.getInt("followers"));
				profile.setAboutDesc(results.getString("userDesc"));
				Blob blob = results.getBlob("profilePic");
				byte[] allBytesInBlob = blob.getBytes(1, (int) blob.length());
				profile.setAvatar(allBytesInBlob);
				profile.setCurrentGame(results.getString("currentGame"));
				
				ArrayList<String> socialName = new ArrayList<String>();
				ArrayList<String> socialLinks = new ArrayList<String>();
				ArrayList<String> gameNames = new ArrayList<String>();
				query = "SELECT * FROM socialLinks WHERE id=" +userID;
				try {
					pstmt = connection.prepareStatement(query);
					ResultSet results2 = pstmt.executeQuery();
						System.out.println(pstmt);
					while (results2.next())
					{
						socialName.add(results2.getString("socialName"));
						socialLinks.add(results2.getString("link"));
					}
					profile.setSocialNames(socialName);
					profile.setSocialLinks(socialLinks);
				} catch (Exception e) {
					e.printStackTrace();
					return profile;
				}
				query = "SELECT * FROM gameLinks WHERE id=" +userID;
				try {
					pstmt = connection.prepareStatement(query);
					ResultSet results3 = pstmt.executeQuery();
						System.out.println(pstmt);
					while(results3.next())
					{
						gameNames.add(results3.getString("gameName"));
					}
					profile.setGameLinks(gameNames);
				} catch (Exception e) {
					e.printStackTrace();
					return profile;
				}
				
			}
			return profile;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return profile;
		}
		finally
		{
			if(pstmt != null)
			{
				pstmt.close();
			}
			connection.close();
		}
	}

	/**
	 * Connect to the DB and do some stuff
	 * @param args standard main args
	 * @throws Exception if error
	 */
	public static void main(String[] args) throws Exception{
		NexusDB app = new NexusDB();
		app.deleteUser("one");
	}
}