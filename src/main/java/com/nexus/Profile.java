package com.nexus;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.bind.DatatypeConverter;

import org.eclipse.jetty.servlet.ServletTester;

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
	private int followers;
	private int posts;
	private String currentGame;
	private String aboutDesc;
	private File avatar;
	private String avatarPic;
	private ArrayList<String> socialNames;
	private ArrayList<String> socialLinks;
	private ArrayList<String> gameLinks;
	
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
	public int getFollowers() {
		return followers;
	}

	/**
	 * Setter for followers
	 * @param followers int
	 */
	public void setFollowers(int followers) {
		this.followers = followers;
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
	public String getAboutDesc() {
		return aboutDesc;
	}

	/**
	 * Setter for aboutDesc.
	 * @param aboutDesc String
	 */
	public void setAboutDesc(String aboutDesc) {
		this.aboutDesc = aboutDesc;
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
		System.out.println(socialNames.get(0));
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

	/**
	 * Getter for arraylist gameLinks.
	 * @return ArrayList Strings
	 */
	public ArrayList<String> getGameLinks() {
		return gameLinks;
	}

	/**
	 * Setter for arraylist gameLinks.
	 * @param gameLinks ArrayList Strings
	 */
	public void setGameLinks(ArrayList<String> gameLinks) {
		this.gameLinks = gameLinks;
	}

	/**
	 * Getter for avatar for frontend request.
	 * @return File
	 */
	public File getAvatar() {
		return avatar;
	}
	
	/**
	 * Getter for avatarPic for profile reload.
	 * @return String
	 */
	public String getAvatarPic() {
		return avatarPic;
	}

	/**
	 * Setter for avatar before going to the database.
	 * @param avatar File
	 */
	public void setAvatar(File avatar) {
//		File image = new File(path);
//		FileInputStream fis = new FileInputStream ( image );
//
//		String sql="insert into imgtst (username,image) values (?, ?)";
//		pst=con.prepareStatement(sql);
//
//		pst.setString(1, user);
//		pst.setBinaryStream (2, fis, (int) file.length() );
		
		// Method to insert the file into database
//	    FileInputStream fis = null;
//	    PreparedStatement ps = null;
//	    try {
//	      conn.setAutoCommit(false);
//	      File file = new File("myPhoto.png");
//	      fis = new FileInputStream(file);
//	      ps = conn.prepareStatement(INSERT_PICTURE);
//	      ps.setString(1, "001");
//	      ps.setString(2, "name");
//	      ps.setBinaryStream(3, fis, (int) file.length());
//	      ps.executeUpdate();
//	      conn.commit();
		this.avatar = avatar;
	}
	
	/**
	 * Setter for avatarPic for the profile reload.
	 * @param avatar byte[]
	 */
	public void setAvatar(byte[] avatar)
	{
		// JDBC Standard get blob
//	    while (resultSet.next()) {
//	        String name = resultSet.getString(1);
//	        String description = resultSet.getString(2);
//	        File image = new File("D:\\java.gif");
//	        FileOutputStream fos = new FileOutputStream(image);
//
//	        byte[] buffer = new byte[1];
//	        InputStream is = resultSet.getBinaryStream(3);
//	        while (is.read(buffer) > 0) {
//	          fos.write(buffer);
//	        }
//	        fos.close();
		byte[] imageBytes = avatar;
		String imageBase64 = DatatypeConverter.printBase64Binary(imageBytes);
		System.out.println(imageBase64);
		avatarPic = imageBase64;
	}
}