package com.lti.services;

import com.lti.daos.UserCollection;
import com.lti.daos.UserDao;
import com.lti.daos.UserFile;
import com.lti.exceptions.UserExistsException;
import com.lti.exceptions.UserNotFoundException;
import com.lti.models.User;

public class UserServiceImpl implements UserService{

	private UserDao ud = new UserFile();
	
	@Override
	public void addUser(User user) throws UserExistsException{
		
		ud.addUser(user);
		
	}

	@Override
	public User getUser(String username) throws UserNotFoundException{
		return ud.getUser(username);
		
	}

	
	
	
}
