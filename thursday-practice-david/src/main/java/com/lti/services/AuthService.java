package com.lti.services;

import com.lti.exceptions.AuthException;
import com.lti.models.User;

public interface AuthService {
	
	public abstract boolean login(User user) throws AuthException;
	

}
