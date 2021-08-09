package com.lti.daos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lti.exceptions.IdMissingException;
import com.lti.models.User;
import com.lti.util.ConnectionUtil;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {

	private static MockedStatic<ConnectionUtil> mockedConnectionUtil;
	private static Connection connection;

	public static Connection getH2Connection() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection("jdbc:h2:~/test");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	@BeforeAll
	public static void init() throws SQLException {
		/*
		 * Mocking the ConnectionUtil class for the getConnectionFromEnv method to
		 * return a connection to the H2 while the mock is "open".
		 */
		mockedConnectionUtil = Mockito.mockStatic(ConnectionUtil.class);
		mockedConnectionUtil.when(ConnectionUtil::getConnectionFromFile).then(I -> getH2Connection());
	}

	@AfterAll
	public static void end() {
		/*
		 * Closing resource, mocked behavior ends.
		 */
		mockedConnectionUtil.close();
	}

	@BeforeEach
	public void setUp() {
		try (Connection c = ConnectionUtil.getConnectionFromFile()) {
			RunScript.execute(c, new FileReader("src/test/resources/setup.sql"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}

	@AfterEach
	public void tearDown() {
		try (Connection c = ConnectionUtil.getConnectionFromFile()) {
			RunScript.execute(c, new FileReader("src/test/resources/teardown.sql"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	UserDao ud = UserPostgres.getUserPostgres();
	User user = new User("newguy", "password", "first", "last", "newguy@email.com", 1);
	User oldUser = new User(1, "username", "password", "first", "last", "user@email.com", 2);
	
	@Test
	public void connectionTest() {
		try {
			Connection con = ConnectionUtil.getConnectionFromFile();
			assertNotNull(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void addUserNotExist() throws SQLException {
		assertEquals(3, ud.addUser(user));
	}
	
	@Test
	public void addUserExist() {
		assertThrows(SQLException.class, () -> ud.addUser(oldUser));
	}
	
	@Test
	public void getUserByUsernameExists() {
		assertEquals(oldUser, ud.getUserByUsername("username"));
	}
	
	@Test
	public void getUserByUsernameNotExists() {
		assertNull(ud.getUserByUsername("hello"));
	}
	
	@Test
	public void getUserByIdExists() {
		assertEquals(oldUser, ud.getUserById(1));
	}
	
	@Test
	public void getUserByIdNotExists() {
		assertNull(ud.getUserById(10));
	}
	
	@Test
	public void updateUser() throws IdMissingException{
		oldUser.setEmail("newemail@email.com");
		assertEquals(1, ud.updateUser(oldUser));
	}
	
	@Test
	public void updateUserNotExists() throws IdMissingException{
		oldUser.setUserId(10);
		oldUser.setEmail("newemail@email.com");
		assertEquals(0, ud.updateUser(oldUser));
	}
	
	@Test
	public void updateUserNoId() {
		oldUser.setUserId(0);
		assertThrows(IdMissingException.class, () -> ud.updateUser(oldUser));
	}
	
	@Test
	public void getUserByRoleExistEmpl() {
		assertEquals(1, ud.getUserByRole("employee").size());
	}
	
	@Test
	public void getUserByRoleExistMang() {
		assertEquals(1, ud.getUserByRole("manager").size());
	}
	
	@Test
	public void getUserByRoleNotExist() {
		assertEquals(0, ud.getUserByRole("role").size());
	}
	
	/*
	@Test
	public void deleteUserExist() {
		assertEquals(1, ud.deleteUser(1));
	}
	
	@Test
	public void deleteUserNotExist() {
		assertEquals(0, ud.deleteUser(10));
	}
	*/
}
