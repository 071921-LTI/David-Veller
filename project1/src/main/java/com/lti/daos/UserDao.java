package com.lti.daos;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;

import com.lti.models.User;

public interface UserDao {
	
	User addUser(User user) throws ConstraintViolationException;
	User getUserByUsername(String username) throws NoResultException;
	boolean updateUser(User user);
	List<User> getUserByRole(String role);

}
