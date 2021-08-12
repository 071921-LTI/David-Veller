package com.lti.services;

import com.lti.exceptions.UnableToRegister;
import com.lti.models.User;

public interface AuthService {
	
	public String login(String username, String password);
	public String register(String username, String password, String firstName, String lastName,
			String email, String role) throws UnableToRegister;
	public User authorize(String token);

}
