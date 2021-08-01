package com.lti.services;

import java.io.IOException;
import java.sql.SQLException;

import com.lti.daos.UserDao;
import com.lti.daos.UserPostgres;
import com.lti.exceptions.AuthException;
import com.lti.exceptions.UserNotFoundException;
import com.lti.models.User;

public class UserServiceImpl implements UserService{
	
	UserDao ud = new UserPostgres();

	@Override
	public User login(String username, String password) throws IOException, SQLException, AuthException, UserNotFoundException {
		// TODO Auto-generated method stub
		User user = ud.getUserByUsername(username);
		
		if (user == null) {
			throw new UserNotFoundException();
		}
		
		if (!user.getPass().equals(password)) {
			throw new AuthException();
		}
		
		return user;
	}

	@Override
	public User registerCustomer(String username, String password) throws AuthException, IOException, SQLException {
		if (password.length() <= 5) {
			throw new AuthException();
		}
		User user = new User();
		user.setId(ud.addUser(username, password, "customer"));
		user.setUser(username);
		user.setPass(password);
		user.setRole("customer");
		return user;
	}

	@Override
	public User registerEmployee(String username, String password) throws AuthException, IOException, SQLException {
		if (password.length() <= 5) {
			throw new AuthException();
		}
		User user = new User();
		user.setId(ud.addUser(username, password, "employee"));
		user.setUser(username);
		user.setPass(password);
		user.setRole("employee");
		return user;
	}

	@Override
	public String getUsernameById(int id) throws AuthException, IOException, SQLException {
		
		return ud.getUserById(id).getUser();
	}

}
