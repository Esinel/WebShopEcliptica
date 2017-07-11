package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	private String firstname;
	private String lastname;
	private String username;
	private String passwordd;
	private int phoneNumber;
	private String email;
	public enum Role {
		ADMIN,
		USER,
		MANAGER
	}
	private Role userType;
	
	
	public User(){}
	
	public User(User user){
		super();
		this.firstname = user.firstname;
		this.lastname = user.lastname;
		this.username = user.username;
		this.passwordd = user.passwordd;
		this.phoneNumber = user.phoneNumber;
		this.email = user.email;
		this.userType = user.userType;
	}
	
	public User(String firstname, String lastname, String username, String passwordd,  int phoneNumber, String email) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.passwordd = passwordd;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.userType = Role.USER;
	}

	
	public String getUsername() {
		return username;
	}
	@JsonProperty("username")
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return passwordd;
	}
	@JsonProperty("passwordd")
	public void setPassword(String password) {
		this.passwordd = password;
	}
	public String getFirstName() {
		return firstname;
	}
	@JsonProperty("firstname")
	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}
	public String getLastName() {
		return lastname;
	}
	@JsonProperty("lastname")
	public void setLastName(String lastname) {
		this.lastname = lastname;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	@JsonProperty("phoneNumber")
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}
	public Role getUserType() {
		return userType;
	}
	@JsonProperty("userType")
	public void setUserType(String role){
		this.userType = Role.valueOf(role);
	}

	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + passwordd + ", firstname=" + firstname + ", lastname="
				+ lastname + ", phoneNumber=" + phoneNumber + ", email=" + email + ", userType=" + userType + "]";
	}
	
	
	
}
