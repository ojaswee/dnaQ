package dnaQ.Objects;

public class User {
	
	public String userID; 
	public String userName; 
	public String department;
	
	public User() {
		super();
	}
	
	public User(String userID,String userName,  String department) {
		super();
		this.userName = userName;
		this.userID = userID;
		this.department = department;
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	

}
