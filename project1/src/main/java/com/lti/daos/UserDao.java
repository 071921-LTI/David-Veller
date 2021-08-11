package com.lti.daos;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;

import com.lti.exceptions.NotFoundException;
import com.lti.models.User;

public interface UserDao {
	
	User addUser(User user) throws ConstraintViolationException;
	User getUserByUsername(String username) throws NoResultException;
	//User getUserById(int userId);
	void updateUser(User user) throws NotFoundException;
	List<User> getUserByRole(String role);
	//int deleteUser(int userId);

}
