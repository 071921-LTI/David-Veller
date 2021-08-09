package com.lti.services;

import com.lti.models.User;

public interface AuthService {
	
	public User login(String username, String password);

}
