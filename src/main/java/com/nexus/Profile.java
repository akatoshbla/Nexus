package com.nexus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This object is used to encapsulate the queries results from the database.
 * Also can be used to encapsulate from the ProfileService calls.
 * @author David Kopp
 *
 */
public class Profile 
{
	private String username;
	private String joined;
	private String lastOnline;
	private String realName;
	private String role;
	private int likes;
	private	int shares;
	private int friends;
	private int posts;
	private String currentGame;
	private String userDesc;
	private String avatar;
	private ArrayList<String> socialNames;
	private ArrayList<String> socialLinks;
	private ArrayList<String> favGameNames;
	private ArrayList<String> favGameLinks; 
	private ArrayList<String> supportedGames;
	
	/**
	 * Simple empty constructor for the Profile object.
	 */
	public Profile()
	{	
	}
	
	/**
	 * Getter for username.
	 * @return String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Setter for username.
	 * @param username String
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Getter for joined
	 * @return String
	 */
	public String getJoined() {
		return joined;
	}

	/**
	 * Setter for joined.
	 * @param joined java.sql.Date
	 */
	public void setJoined(java.sql.Date joined) {
		Date date = joined;
		DateFormat df = new SimpleDateFormat("MM/dd/YYYY"); // TODO: need to fix format to monthname day, year
		this.joined = df.format(date);
	}

	/**
	 * Getter for lastOnline date.
	 * @return String
	 */
	public String getLastOnline() {
		return lastOnline;
	}

	/**
	 * Setter for lastOnline.
	 * @param lastOnline java.sql.Date
	 */
	public void setLastOnline(java.sql.Date lastOnline) {
		Date date = lastOnline;
		DateFormat df = new SimpleDateFormat("MM/dd/YYYY"); // TODO: need to fix format to monthname day, year
		this.lastOnline = df.format(date);
	}

	/**
	 * Getter for realName
	 * @return String
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * Setter for realName
	 * @param realName String
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * Getter for role
	 * @return String
	 */
	public String getRole() {
		return role;
	}

	/**
	 * Setter for role.
	 * @param role String
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Getter for likes.
	 * @return int
	 */
	public int getLikes() {
		return likes;
	}

	/**
	 * Setter for likes.
	 * @param likes int
	 */
	public void setLikes(int likes) {
		this.likes = likes;
	}

	/**
	 * Getter for shares.
	 * @return int
	 */
	public int getShares() {
		return shares;
	}

	/**
	 * Setter for shares.
	 * @param shares int
	 */
	public void setShares(int shares) {
		this.shares = shares;
	}
	
	/**
	 * Getter for followers
	 * @return int
	 */
	public int getFriends() {
		return friends;
	}

	/**
	 * Setter for followers
	 * @param followers int
	 */
	public void setFriends(int followers) {
		this.friends = followers;
	}

	/**
	 * Getter for posts.
	 * @return int
	 */
	public int getPosts() {
		return posts;
	}

	/**
	 * Setter for posts.
	 * @param posts int
	 */
	public void setPosts(int posts) {
		this.posts = posts;
	}

	/**
	 * Getter for currentGame.
	 * @return String
	 */
	public String getCurrentGame() {
		return currentGame;
	}

	/**
	 * Setter for currentGame.
	 * @param currentGame String
	 */
	public void setCurrentGame(String currentGame) {
		this.currentGame = currentGame;
	}

	/**
	 * Getter for aboutDesc.
	 * @return String
	 */
	public String getUserDesc() {
		return userDesc;
	}

	/**
	 * Setter for aboutDesc.
	 * @param aboutDesc String
	 */
	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	
	/**
	 * Getter for arraylist socialNames.
	 * @return ArrayList String
	 */
	public ArrayList<String> getSocialNames() {
		return socialNames;
	}

	/**
	 * Setter for arraylist socialNames.
	 * @param socialNames ArrayList String
	 */
	public void setSocialNames(ArrayList<String> socialNames) {
		this.socialNames = socialNames;
	}

	/**
	 * Getter for arraylist socialLinks.
	 * @return ArrayList Strings
	 */
	public ArrayList<String> getSocialLinks() {
		return socialLinks;
	}

	/**
	 * Setter for arraylist socialLinks.
	 * @param socialLinks ArrayList Strings
	 */
	public void setSocialLinks(ArrayList<String> socialLinks) {
		this.socialLinks = socialLinks;
	}
	
	public ArrayList<String> getFavGameNames() {
		return favGameNames;
	}
	
	public void setFavGameNames(ArrayList<String> favGameNames) {
		this.favGameNames = favGameNames;
	}
	
	public ArrayList<String> getFavGameLinks() {
		return favGameLinks;
	}
	
	public void setFavGameLinks(ArrayList<String> favGameLinks) {
		this.favGameLinks = favGameLinks;
	}

	/**
	 * Getter for arraylist gameLinks.
	 * @return ArrayList Strings
	 */
	public ArrayList<String> getSupportedGames() {
		return supportedGames;
	}

	/**
	 * Setter for arraylist gameLinks.
	 * @param supportedGames ArrayList Strings
	 */
	public void setSupportedGames(ArrayList<String> supportedGames) {
		this.supportedGames = supportedGames;
	}

	/**
	 * Getter for avatar for frontend request.
	 * @return String
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Setter for avatar before going to the database.
	 * @param avatar String
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
}