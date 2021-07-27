package com.lti.services;

import com.lti.exceptions.LoginFail;
import com.lti.models.User;

public class AuthService implements AuthServiceable{

	@Override
	public User login(String username, String password) throws LoginFail{
		
		return null;
	}

	@Override
	public User register(String username, String password) throws LoginFail {
		
		return null;
	}

}
