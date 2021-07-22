package com.lti.daos;

import com.lti.exceptions.UserExistsException;
import com.lti.exceptions.UserNotFoundException;
import com.lti.models.User;

public interface UserDao {
	
	
	//methods such as add and get user
	
	public abstract User getUser(String username) throws UserNotFoundException;
	public abstract boolean addUser(User user) throws UserExistsException;

}
