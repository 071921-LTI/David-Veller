package com.lti.services;

import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;

import com.lti.daos.UserDao;
import com.lti.daos.UserHibernate;
import com.lti.exceptions.LoginException;
import com.lti.models.User;
import com.lti.models.UserRole;

public class AuthServiceImpl implements AuthService {

	private static AuthService authServiceImpl;

	private AuthServiceImpl() {
	}

	public static AuthService getAuthService() {
		if (authServiceImpl == null) {
			authServiceImpl = new AuthServiceImpl();
		}
		return authServiceImpl;
	}

	UserDao ud = UserHibernate.getUserHibernate();

	public static String tokenize(User user) {
		return user.getUsername() + ":" + user.getRole().getRole();
	}

	@Override
	public String login(String username, String password) throws LoginException, NoResultException {
		User user = ud.getUserByUsername(username);
		if (!user.getPassword().equals(password)) {
			throw new LoginException();
		} else {
			return tokenize(user);
		}
	}

	@Override
	public String register(String username, String password, String firstName, String lastName, String email,
			String role) throws LoginException, ConstraintViolationException {
		UserRole ur;
		if (role.equals("employee")) {
			ur = new UserRole(2, "employee");
		} else if (role.equals("manager")) {
			ur = new UserRole(1, "manager");
		} else {
			throw new LoginException();
		}
		User newUser = new User(username, password, firstName, lastName, email, ur);
		newUser = ud.addUser(newUser);
		return tokenize(newUser);
	}

	@Override
	public String authorize(String token) {
		if (token == null) {
			return null;
		} else {
			return token.split(":")[0];
		}
	}

}
