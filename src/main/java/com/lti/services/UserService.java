package com.lti.services;

import com.lti.exceptions.UserExistsException;
import com.lti.exceptions.UserNotFoundException;
import com.lti.models.User;

public interface UserService {
	
	//does user exist?
	public abstract void addUser(User user) throws UserExistsException;
	public abstract User getUser(String username) throws UserNotFoundException;
	
}
