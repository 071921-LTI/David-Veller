package com.lti.daos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lti.exceptions.NotFoundException;
import com.lti.models.Reimb;
import com.lti.models.ReimbStatus;
import com.lti.models.ReimbType;
import com.lti.models.User;
import com.lti.models.UserRole;
import com.lti.util.HibernateUtil;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class UserHibernateTest {
	
	private static MockedStatic<HibernateUtil> mockedHibernateUtil;
	private static SessionFactory sf;

	public static SessionFactory getH2Connection() {
		if (sf == null || sf.isClosed()) {
			Configuration cg = new Configuration();
			cg.configure("hibernate.cfg.xml");
			cg.setProperty("hibernate.connection.url",
					"jdbc:h2:~/test;INIT=runscript from 'src/test/resources/setup.sql'");
			cg.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			cg.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
			cg.setProperty("hibernate.hbm2ddl.auto", "validate");
			sf = cg.buildSessionFactory();
		}
		return sf;
	}

	@BeforeAll
	public static void init() throws SQLException {
		/*
		 * Mocking the ConnectionUtil class for the getConnectionFromEnv method to
		 * return a connection to the H2 while the mock is "open".
		 */
		mockedHibernateUtil = Mockito.mockStatic(HibernateUtil.class);
		mockedHibernateUtil.when(HibernateUtil::getSessionFactory).then(I -> getH2Connection());
	}

	@AfterAll
	public static void end() {
		/*
		 * Closing resource, mocked behavior ends.
		 */
		mockedHibernateUtil.close();
	}

	UserDao ud = UserHibernate.getUserHibernate();
	
	ReimbStatus rs = new ReimbStatus(1, "pending");
	ReimbType rt = new ReimbType(1, "LODGING");
	UserRole empl = new UserRole(2, "employee");
	UserRole man = new UserRole(1, "manager");
	User newUser = new User("newUser", "password", "first", "last", "newuser@email.com", empl);
	User emplUser = new User(1, "username", "password", "first", "last", "user@email.com", empl);
	User manUser = new User(2, "david", "password", "first", "last", "david@email.com", man);
	Reimb reimb = new Reimb(30, Timestamp.valueOf(LocalDateTime.now()), emplUser, rs, rt);
	
	@Test
	@Order(1)
	public void connectionTest() {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			assertNotNull(s);
		}
	}
	
	@Test
	@Order(2)
	public void addUserNotExist() {
		assertEquals(newUser, ud.addUser(newUser));
	}
	
	@Test
	@Order(3)
	public void addUserExist() {
		User upUser = new User("newUser", "password", "first", "last", "newuser@email.com", empl);
		assertThrows(ConstraintViolationException.class, () -> ud.addUser(upUser));
	}
	
	@Test
	@Order(4)
	public void getUserByUsernameExists() {
		assertEquals(emplUser, ud.getUserByUsername("username"));
	}
	
	@Test
	@Order(5)
	public void getUserByUsernameNotExists() {
		assertThrows(NoResultException.class, () -> ud.getUserByUsername("hello"));
	}
	

	
	@Test
	@Order(6)
	public void updateUser(){
		User upUser = ud.getUserByUsername("username");
		upUser.setEmail("newemail@email.com");
		ud.updateUser(upUser);
		assertEquals(upUser.getEmail(), ud.getUserByUsername("username").getEmail());
	}
	
	@Test
	@Order(7)
	public void updateUserNotExists(){
		User upUser = emplUser;
		upUser.setUserId(10);
		assertEquals(ud.updateUser(upUser), false);
	}
	
	@Test
	@Order(8)
	public void getUserByRoleExistEmpl() {
		assertEquals(2, ud.getUserByRole("employee").size());
	}
	
	@Test
	@Order(9)
	public void getUserByRoleExistMang() {
		assertEquals(1, ud.getUserByRole("manager").size());
	}
	
	@Test
	@Order(10)
	public void getUserByRoleNotExist() {
		assertEquals(0, ud.getUserByRole("role").size());
	}

}
