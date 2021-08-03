package com.lti.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lti.daos.UserDao;
import com.lti.exceptions.AuthException;
import com.lti.exceptions.UserNotFoundException;
import com.lti.models.User;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	String hashedPass = HashPass.getHasher().hashPass("password");
	
	@Mock
	private UserDao ud;

	@InjectMocks
	private UserService us = UserServiceImpl.getUserService();

	@Test
	public void getUserService() {
		assertEquals(us, UserServiceImpl.getUserService());
	}
	
	@Test
	public void registerManager() {
		try {
			Mockito.when(ud.addUser("david", hashedPass, "manager")).thenReturn(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(new User(1, "david", hashedPass, "manager"), us.registerManager("david", "password"));
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void registerManagerExists() {
		try {
			Mockito.when(ud.addUser("david", hashedPass, "manager")).thenThrow(SQLException.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThrows(SQLException.class, () -> us.registerManager("david", "password"));
	}
	
	@Test
	public void registerManagerShortpass() {
		assertThrows(AuthException.class, () -> us.registerManager("david", "pass"));
	}
	
	@Test
	public void getEmployees() {
		List<User> employees = new ArrayList<>();
		employees.add(new User(1, "hello", "pass", "employee"));
		employees.add(new User(2, "bye", "pass", "employee"));
		try {
			Mockito.when(ud.getEmployees()).thenReturn(employees);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(2, us.getEmployees().size());
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void fireEmployeeExists() {
		try {
			Mockito.when(ud.removeUser(1)).thenReturn(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(1, us.fireEmployee(1));
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void fireEmployeeNotExists() {
		try {
			Mockito.when(ud.removeUser(1)).thenReturn(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(0, us.fireEmployee(1));
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getUsernameById() {

		try {
			Mockito.when(ud.getUserById(1)).thenReturn(new User(1, "david", hashedPass, "customer"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String expected = "david";
		try {
			assertEquals(expected, us.getUsernameById(1));
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void login() {
		try {
			Mockito.when(ud.getUserByUsername("david")).thenReturn(new User(1, "david", hashedPass, "customer"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(new User(1, "david", hashedPass, "customer"), us.login("david", "password"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void loginNoUser() {
		try {
			Mockito.when(ud.getUserByUsername("david")).thenReturn(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThrows(UserNotFoundException.class, () -> us.login("david", "password"));
	}
	
	@Test
	public void loginWrongPass() {
		try {
			Mockito.when(ud.getUserByUsername("david")).thenReturn(new User(1, "david", hashedPass, "customer"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThrows(AuthException.class, () -> us.login("david", "wrongpassword"));
	}
	
	@Test
	public void registerCustomer() {
		try {
			Mockito.when(ud.addUser("david", hashedPass, "customer")).thenReturn(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(new User(1, "david", hashedPass, "customer"), us.registerCustomer("david", "password"));
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void registerCustomerExists() {
		try {
			Mockito.when(ud.addUser("david",hashedPass, "customer")).thenThrow(SQLException.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThrows(SQLException.class, () -> us.registerCustomer("david", "password"));
	}
	
	@Test
	public void registerCustomerShortpass() {
		assertThrows(AuthException.class, () -> us.registerCustomer("david", "pass"));
	}
	
	@Test
	public void registerEmployee() {
		try {
			Mockito.when(ud.addUser("david", hashedPass, "employee")).thenReturn(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			assertEquals(new User(1, "david", hashedPass, "employee"), us.registerEmployee("david", "password"));
		} catch (AuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void registerEmployeeExists() {
		try {
			Mockito.when(ud.addUser("david", hashedPass, "employee")).thenThrow(SQLException.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThrows(SQLException.class, () -> us.registerEmployee("david", "password"));
	}
	
	@Test
	public void registerEmployeeShortpass() {
		assertThrows(AuthException.class, () -> us.registerEmployee("david", "pass"));
	}
}
