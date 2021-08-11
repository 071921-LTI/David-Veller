package com.lti.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lti.daos.UserDao;
import com.lti.models.User;
import com.lti.models.UserRole;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	UserRole empl = new UserRole(2, "employee");
	User user = new User("newUser", "password", "first", "last", "newuser@email.com", empl);
	
	@Mock
	UserDao ud;
	
	@InjectMocks
	UserService us = UserServiceImpl.getUserService();
	
	@Test
	public void addUserNotExist() {
		user.setUserId(1);
		Mockito.when(ud.addUser(user)).thenReturn(user);
		assertEquals(user, us.addUser(user));
	}
	
	@Test
	public void addUserExist() {
		Mockito.when(ud.addUser(user)).thenThrow(ConstraintViolationException.class);
		assertEquals(null, us.addUser(user));
	}
	
	@Test
	public void getUserByUsernameExist() {
		Mockito.when(ud.getUserByUsername(user.getUsername())).thenReturn(user);
		assertEquals(user, us.getUserByUsername(user.getUsername()));
	}
	
	@Test
	public void getUserByUsernameNotExist() {
		Mockito.when(ud.getUserByUsername(user.getUsername())).thenThrow(NoResultException.class);
		assertNull(us.getUserByUsername(user.getUsername()));
	}
	
	@Test
	public void updateUserExist() {
		Mockito.when(ud.updateUser(user)).thenReturn(true);
		assertEquals(true, us.updateUser(user));
	}
	
	@Test
	public void updateUserNotExist() {
		Mockito.when(ud.updateUser(user)).thenReturn(false);
		assertEquals(us.updateUser(user), false);
	}
	
	@Test
	public void getUserByRole() {
		List<User> empls = new ArrayList<>();
		empls.add(user);
		Mockito.when(ud.getUserByRole("employee")).thenReturn(empls);
		assertEquals(us.getUserByRole("employee"), empls);
	}
	

}
