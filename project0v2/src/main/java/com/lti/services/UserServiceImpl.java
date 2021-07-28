package com.lti.services;

import java.io.IOException;
import java.sql.SQLException;

import com.lti.daos.UserPostgres;
import com.lti.models.User;

public class UserServiceImpl implements UserService{

	@Override
	public User login(String username, String password) throws IOException, SQLException {
		// TODO Auto-generated method stub
		UserPostgres up = new UserPostgres();
		User user = up.getUserByUsername(username);
		
		
		return null;
	}

	@Override
	public User registerCustomer(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User registerEmployee(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
