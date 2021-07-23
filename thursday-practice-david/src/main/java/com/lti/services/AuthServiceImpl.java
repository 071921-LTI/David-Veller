package com.lti.services;

import com.lti.daos.UserCollection;
import com.lti.daos.UserDao;
import com.lti.exceptions.AuthException;
import com.lti.exceptions.UserNotFoundException;
import com.lti.models.User;

public class AuthServiceImpl implements AuthService{

	private UserDao ud = new UserCollection();
	
	@Override
	public boolean login(User user) throws AuthException {
		
		User userChecked = new User();
		try {
			userChecked = ud.getUser(user.getUsername());
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("User was not found");
		}
		
		if (user.getPassword().equals(userChecked.getPassword())) {
			return true;
		}
		
		throw new AuthException();
	}

}
