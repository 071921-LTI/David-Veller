package com.lti.daos;

import java.sql.SQLException;
import java.util.List;

import com.lti.models.User;

public interface UserDao {
	
	User addUser(User user) throws SQLException;
	User getUserByUsername(String username);
	//User getUserById(int userId);
	void updateUser(User user);
	List<User> getUserByRole(String role);
	//int deleteUser(int userId);

}
