package com.lti.services;

import com.lti.exceptions.LoginFail;
import com.lti.models.User;

public interface AuthServiceable {
	
	public User login(String username, String password) throws LoginFail;
	public User register(String username, String password) throws LoginFail;

	
	
	
	
}
