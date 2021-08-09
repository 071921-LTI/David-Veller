package com.lti.daos;

import java.sql.SQLException;
import java.util.List;

import com.lti.exceptions.IdMissingException;
import com.lti.models.User;

public interface UserDao {
	
	int addUser(User user) throws SQLException;
	User getUserByUsername(String username);
	User getUserById(int userId);
	int updateUser(User user) throws IdMissingException;
	List<User> getUserByRole(String role);
	//int deleteUser(int userId);

}
