package com.lti.models;

public class User {
	
	
	private String username;
	private String password;
	private char role;
	private int id;
	
	public User() {
		super();
	}
	
	

	public User(int id, String username, String password, char role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public char getRole() {
		return role;
	}
	public void setRole(char role) {
		this.role = role;
	}
	
	

}
