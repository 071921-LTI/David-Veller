package com.lti.daos;

import java.util.ArrayList;

import com.lti.exceptions.UserExistsException;
import com.lti.exceptions.UserNotFoundException;
import com.lti.models.User;

public class UserCollection implements UserDao{

	ArrayList<User> uc = new ArrayList<>();
	
	
	@Override
	public User getUser(String username) throws UserNotFoundException{
		for (User u : uc) {
			if (u.getUsername().equals(username)){
				return u;
			}
		}
		throw new UserNotFoundException();
	}

	@Override
	public boolean addUser(User user) throws UserExistsException{
		
		if (uc.indexOf(user) < 0) {
			uc.add(user);
			return true;
		}
		throw new UserExistsException();
		
	}
	
	

}
