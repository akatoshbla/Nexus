package com.nexus;
import java.sql.Connection;
//import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.Statement;
import java.util.Properties;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NexusDB {

	private final String userName = "root";
	private final String password = "1234abcd";
	private final String serverName = "localhost";
	private final int portNumber = 3306;
	private final String dbName = "nexus";
	//private final String tableName = "users";


	//private ResultSet resultSet = null;
	/**
	 *Hashes a string using SHA-256
	 */
	@SuppressWarnings("restriction")
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
	 *Delete's a user from the database with the inputed name
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
		finally {
			
			if (pstmt!=null) pstmt.close();
			conn.close();

		}
	}
	/**
	 *Creates a user record in the database, with the inputed name,password pair.
	 *Only creates a record if there isn't already a record with the same name.
	 */
	public Boolean createUser (String name, String password) throws Exception{
		//password = hashPassword(password);
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
		try {
			if (recordExists(name)) 
				return false; //already exists
			else {
		     String command = "INSERT into users VALUES(?,?)";
		     pstmt = conn.prepareStatement(command);
		     pstmt.setString(1, name);
		     pstmt.setString(2, password);
		     pstmt.executeUpdate(); 
		     
		     System.out.println(pstmt);
		     return true;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		finally {
			if (pstmt!=null) pstmt.close();
			conn.close();
			
		}
		
	}
	/**
	 *Checks if a record already exisets in the database with the inputed name.
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
	 *Updates a user's password in the database with the inputed password string.
	 */
	public void updatePassword(String name, String password) throws Exception{
		password = hashPassword(password);
		Connection conn=this.getConnection();
		PreparedStatement pstmt=null;
		try {
			
			String command = "UPDATE users SET password = ? WHERE name = ?";
			pstmt=conn.prepareStatement(command);
			pstmt.setString(1, password);
			pstmt.setString(2, name);
			
					System.out.println(pstmt);
			pstmt.executeUpdate();

		}
		finally {
			if (pstmt!=null) pstmt.close();
			conn.close();
		}
		
	}
	/**
	 *Retrieves the inputed user's hashed password from the database.
	 */
	public String retrievePassword(String name) throws Exception{
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
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
			finally {
				if (pstmt!=null) pstmt.close();
				conn.close();
			}
	
		
	}

	/**
	 * Connect to the DB and do some stuff
	 */
	public static void main(String[] args) throws Exception{
		NexusDB app = new NexusDB();
		System.out.println(app.recordExists("bnc"));
		
		


	}
}