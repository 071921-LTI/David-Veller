package com.lti.services;

import java.util.List;

import com.lti.models.User;

public interface UserService {
	
	User addUser(User user);
	User getUserByUsername(String username);
	boolean updateUser(User user);
	List<User> getUserByRole(String role);

}
