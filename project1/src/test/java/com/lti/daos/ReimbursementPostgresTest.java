package com.lti.daos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
import com.lti.models.Reimb;
import com.lti.util.ConnectionUtil;

@ExtendWith(MockitoExtension.class)
public class ReimbursementPostgresTest {

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
	
	ReimbursementDao rd = ReimbursementPostgres.getReimbursementPostgres();
	Reimb reimb = new Reimb(30, Timestamp.valueOf(LocalDateTime.now()), 2, 1, 1);
	
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
	public void addReimb() {
		assertEquals(3, rd.addReimb(reimb));
	}
	
	@Test
	public void getReimbByStatusAndUser1() {
		assertEquals(2, rd.getReimbByStatusAndUser("pending", 1).size());
	}
	
	@Test
	public void getReimbByStatusAndUser2() {
		assertEquals(0, rd.getReimbByStatusAndUser("pending", 2).size());
	}
	
	@Test
	public void getReimbByStatusAndUser3() {
		assertEquals(0, rd.getReimbByStatusAndUser("resolved", 1).size());
	}
	
	
	@Test
	public void updateReimb() throws IdMissingException {
		reimb.setReimbId(1);
		reimb.setAuthor(2);
		reimb.setResolver(2);
		assertEquals(rd.updateReimb(reimb), 1);
	}
	
	@Test
	public void updateReimbNotExist() throws IdMissingException {
		reimb.setReimbId(10);
		assertEquals(rd.updateReimb(reimb), 0);
	}
	
	@Test
	public void updateReimbNoId() {
		Reimb testReimb = new Reimb(30, Timestamp.valueOf(LocalDateTime.now()), 2, 1, 1);
		assertThrows(IdMissingException.class, () -> rd.updateReimb(testReimb));
	}
	
	@Test
	public void getReimbByStatusExists() {
		assertEquals(2, rd.getReimbByStatus("pending").size());
	}
	
	@Test
	public void getReimbByStatusNotExists() {
		assertEquals(0, rd.getReimbByStatus("resolved").size());
	}
	
	@Test
	public void getReimbByUserExists() {
		assertEquals(2, rd.getReimbByUser(1).size());
	}
	
	@Test
	public void getReimbByUserNotExists() {
		assertEquals(0, rd.getReimbByUser(2).size());
	}
	
	@Test
	public void deleteReimbExists() {
		assertEquals(1, rd.deleteReimb(1));
	}
	
	@Test
	public void deleteReimbNotExists() {
		assertEquals(0, rd.deleteReimb(10));
	}
	
}
