package com.lti.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.lti.exceptions.AuthException;
import com.lti.exceptions.UserNotFoundException;
import com.lti.models.User;

public interface UserService {
	
	public abstract User login(String username, String password) throws IOException, SQLException, AuthException, UserNotFoundException;
	public abstract User registerCustomer(String username, String password) throws AuthException, IOException, SQLException;
	public abstract User registerEmployee(String username, String password) throws AuthException, IOException, SQLException;
	public abstract User registerManager(String username, String password) throws AuthException, IOException, SQLException;
	public abstract String getUsernameById(int id) throws AuthException, IOException, SQLException;
	public abstract int fireEmployee(int emplId) throws AuthException, IOException, SQLException;
	public abstract List<User> getEmployees() throws AuthException, IOException, SQLException;

}
