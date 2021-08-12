package com.lti.services;

import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;

import com.lti.exceptions.LoginException;

public interface AuthService {
	
	public String login(String username, String password) throws LoginException, NoResultException;
	public String register(String username, String password, String firstName, String lastName,
			String email, String role) throws LoginException, ConstraintViolationException;
	public String authorize(String token);

}
