package com.lti.services;

import com.lti.models.User;

public interface UserService {
	
	//does user exist?
	public abstract void addUser(User user);
	public abstract User getUser(String username);
	
}
