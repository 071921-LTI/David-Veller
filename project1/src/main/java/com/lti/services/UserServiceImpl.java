package com.lti.services;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;

import com.lti.daos.UserDao;
import com.lti.daos.UserHibernate;
import com.lti.models.User;

public class UserServiceImpl implements UserService{
	
	private static UserService userServiceImpl;
	
	private UserServiceImpl() {}
	
	public static UserService getUserService() {
		if (userServiceImpl == null) {
			userServiceImpl = new UserServiceImpl();
		}
		return userServiceImpl;
	}

	UserDao ud = UserHibernate.getUserHibernate();
	
	@Override
	public User addUser(User user) {
		User newUser = null;
		try{
			newUser = ud.addUser(user);
			return newUser;
		}catch (ConstraintViolationException e) {
			return null;
		}
	}

	@Override
	public User getUserByUsername(String username) {
		User user = null;
		try{
			user = ud.getUserByUsername(username);
			return user;
		}catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public boolean updateUser(User user) {
		return ud.updateUser(user);
	}

	@Override
	public List<User> getUserByRole(String role) {
		return ud.getUserByRole(role);
	}


}
