package com.nexus;
import java.sql.Connection;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NexusDB {

	private final String userName = "root";
	private final String password = "1234abcd";
	private final String serverName = "localhost";
	private final int portNumber = 3306;
	private final String dbName = "nexus";
	private final String tableName = "users";
	//private PreparedStatement preparedStatement = null;
	private Connection conn = null;
	private Statement stmt = null;
	//private ResultSet resultSet = null;

	public String hashPassword(String password) throws Exception  {
		try {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes("UTF-8"));
		byte[] digest = md.digest();
		return javax.xml.bind.DatatypeConverter.printHexBinary(digest).toString();
		}
		catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	public Connection getConnection() throws SQLException {
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		conn = DriverManager.getConnection("jdbc:mysql://"
				+ this.serverName + ":" + this.portNumber + "/" + this.dbName,
				connectionProps);
		return conn;
	}

	public void deleteData(String name) throws SQLException {
		
		conn=this.getConnection();
		try {
			stmt = conn.createStatement();
			String command = "DELETE from " + tableName + " WHERE name = \"" + name + "\";";
			System.out.println(command);
			stmt.executeUpdate(command);
			System.out.println("Delete entries with name: " + name + "from table users.");
		}
		finally {
			if (stmt!=null) stmt.close();
		}
		conn.close();
	}
	public void insertData (String name, String password) throws Exception{
		password = hashPassword(password);
		conn=this.getConnection();
		try {
			
		     stmt = conn.createStatement();
		     String command = "INSERT into " + tableName + " VALUES(\"" + name + "\",\"" + password + "\");";
		     stmt.executeUpdate(command); 
		     System.out.println("Enetered Name,Password pair " + name + "," + password +" into table " + tableName );
		}
		finally {
			if (stmt!=null) stmt.close();
			
		}
		conn.close();
	}
	public void updatePassword(String name, String password) throws Exception{
		password = hashPassword(password);
		conn=this.getConnection();
		try {
			stmt=conn.createStatement();
			String command = "UPDATE users SET password = \"" + password + "\" WHERE name = \"" + name + "\"";
			stmt.executeUpdate(command);
			System.out.println("Updated record(s) with name = " + name + " to password = " + password);
		}
		finally {
			if (stmt!=null) stmt.close();
		}
		conn.close();
	}
	public String retrievePasswordHash(String name) throws Exception{
		conn=this.getConnection();
		try {
			stmt=conn.createStatement();
			String command = "SELECT password FROM " + tableName + " WHERE name=\"" + name + "\"";
			System.out.println(command);
			ResultSet results = stmt.executeQuery(command);
			if (results.next())
				return results.getString("password");
			else return "";
		}
		catch(Exception e){
			return "";
		}
		finally {
			if (stmt!=null) stmt.close();
			conn.close();
		}
	
		
	}
	public void printTable() {
		
	
	}
	/**
	 * Connect to the DB and do some stuff
	 */
	public static void main(String[] args) throws Exception{
		NexusDB app = new NexusDB();
		
		


	}
}