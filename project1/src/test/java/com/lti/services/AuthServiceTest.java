package com.lti.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.NoResultException;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lti.daos.UserDao;
import com.lti.exceptions.LoginException;
import com.lti.models.User;
import com.lti.models.UserRole;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
	
	UserRole empl = new UserRole(2, "employee");
	User user = new User("newUser", "password", "first", "last", "newuser@email.com", empl);
	
	@Mock
	UserDao ud;
	
	@InjectMocks
	AuthService as =  AuthServiceImpl.getAuthService();
	
	@Test
	public void loginSuccess() throws LoginException {
		Mockito.when(ud.getUserByUsername("newUser")).thenReturn(user);
		assertEquals("newUser:employee", as.login("newUser", "password"));
	}
	
	@Test
	public void loginFailure() {
		Mockito.when(ud.getUserByUsername("newUser")).thenReturn(user);
		assertThrows(LoginException.class, () -> as.login("newUser", "wrong"));
	}
	
	@Test
	public void userNotFound() {
		Mockito.when(ud.getUserByUsername("newUser")).thenThrow(NoResultException.class);
		assertThrows(NoResultException.class, () -> as.login("newUser", "password"));
	}
	
	@Test
	public void registerSucess() throws LoginException {
		Mockito.when(ud.addUser(user)).thenReturn(user);
		assertEquals("newUser:employee", as.register("newUser", "password", "first", "last", "newuser@email.com", "employee"));
	}
	
	@Test
	public void registerFailure() {
		Mockito.when(ud.addUser(user)).thenThrow(ConstraintViolationException.class);
		assertThrows(ConstraintViolationException.class, () -> as.register("newUser", "password", "first", "last", "newuser@email.com", "employee"));
	}
	
	@Test
	public void registerNoRole() {
		assertThrows(LoginException.class, () -> as.register("newUser", "password", "first", "last", "newuser@email.com", "role"));
	}
	
	@Test
	public void authroizeValid() {
		assertEquals("newUser", as.authorize("newUser:employee"));
	}
	
	@Test
	public void authroizeInvalid() {
		assertNull(as.authorize(null));
	}
	
}
