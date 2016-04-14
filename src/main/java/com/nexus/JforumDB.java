package com.nexus;

import java.sql.*;

import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static java.util.Arrays.asList;

public class JforumDB {

	private final String userName = "root";
	private final String password = "1234abcd";
	private final String serverName = "localhost";
	private final int portNumber = 3306;
	private final String dbName = "jforum";
	
	/**
	 * Hashes a string using the specified algorithm. 
	 * Alg input should "SHA-256" or "MD5" for our purposes.
	 * @param password String
	 * @param alg String
	 * @return String
	 * @throws Exception if error
	 */
	public String hashPassword(String password, String alg) throws Exception {
		try 
		{
			MessageDigest md = MessageDigest.getInstance(alg);
			md.update(password.getBytes("UTF-8"));
			byte[] digest = md.digest();
			return javax.xml.bind.DatatypeConverter.printHexBinary(digest).toString(); //converts byte array to hex string
		}
		catch (Exception e) {
			e.printStackTrace();
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
	public boolean createUser(String username, String passwordHash, String email) 
			throws SQLException{
		String statement = "INSERT INTO jforum_users(user_active, username, "
						 + "user_password, user_regdate, user_email) "
						 + "VALUES(1, ?, ?, NOW(), ?);";
		
		List<String> params = asList(username,passwordHash,email);
		return updateHelper(statement,params) != -1;
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
	@SuppressWarnings("unused")
	private List<HashMap<String,Object>> queryHelper(String query, List<?> params) 
			throws SQLException
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
			while (result.next()){
				HashMap<String,Object> row = new HashMap<String,Object>(colCount);
				for(i = 1; i<=colCount; i++) {
					row.put(md.getColumnName(i), result.getObject(i));
				}
				resultList.add(row);
			}
			return resultList;		
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
}
