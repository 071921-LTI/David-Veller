package com.lti.daos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

import com.lti.util.ConnectionUtil;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {
	
	private UserDao ud = UserPostgres.getUserPostgres();
	
	private static MockedStatic<ConnectionUtil> mockedConnectionUtil;
	private static Connection connection;
	
	
	@Test
	public void getUserByIdExists() {
		try {
			assertNotNull(ud.getUserById(1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getUserByIdNotExists() {
		try {
			assertNull(ud.getUserById(2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getUserByUsernameExists() {
		try {
			assertNotNull(ud.getUserByUsername("david"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getUserByUsernameNotExists() {
		try {
			assertNull(ud.getUserByUsername("test"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void addNewDuplicateUser() {
		assertThrows(SQLException.class, () -> ud.addUser("david", "hello123", "customer"));
	}
	
	@Test
	public void addNewUserShortPass() {
		assertThrows(SQLException.class, () -> ud.addUser("newuser", "he", "customer"));
	}
	
	/*
	@Test
	public void addNewUser() {
		int expected = 2;
		int actualResult = 0;
		
		try {
			actualResult = ud.addUser("newuseasdasdsasr", "hello123", "customer");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expected, actualResult);
	}
	*/
	
	
	
	
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
		 * Mocking the ConnectionUtil class for the getConnectionFromEnv method to return
		 * a connection to the H2 while the mock is "open".
		 */
		mockedConnectionUtil = Mockito.mockStatic(ConnectionUtil.class);
		mockedConnectionUtil.when(ConnectionUtil::getConnectionFromFile)
				.then(I -> getH2Connection());
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
			RunScript.execute(c, new FileReader("setupUserDao.sql"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@AfterEach
	public void tearDown() {
		try (Connection c = ConnectionUtil.getConnectionFromFile()) {
			RunScript.execute(c, new FileReader("teardownUserDao.sql"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
