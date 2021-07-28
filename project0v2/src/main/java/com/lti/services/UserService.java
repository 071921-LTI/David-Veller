package com.lti.services;

import java.io.IOException;
import java.sql.SQLException;

import com.lti.models.User;

public interface UserService {
	
	public abstract User login(String username, String password) throws IOException, SQLException;
	public abstract User registerCustomer(String username, String password);
	public abstract User registerEmployee(String username, String password);

}
