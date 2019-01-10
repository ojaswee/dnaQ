package dnaQ.Models;

public class User {
	
	private String userID;
	private String userName;

	public User() {
		super();
	}
	
	public User(String userID,String userName) {
		super();
		this.userName = userName;
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}

}
