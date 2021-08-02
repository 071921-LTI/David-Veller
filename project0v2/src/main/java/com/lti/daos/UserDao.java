package com.lti.daos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.lti.models.User;

public interface UserDao {
	
	public abstract User getUserByUsername(String username) throws IOException, SQLException;
	public abstract User getUserById(int userId) throws IOException, SQLException;
	public abstract int addUser(String username, String password, String role) throws IOException, SQLException;
	public abstract List<User> getEmployees() throws IOException, SQLException;
	public abstract int removeUser(int userId) throws IOException, SQLException;

}
