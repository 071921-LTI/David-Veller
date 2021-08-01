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

import com.lti.models.Item;
import com.lti.util.ConnectionUtil;

import net.bytebuddy.agent.builder.AgentBuilder.Identified;

@ExtendWith(MockitoExtension.class)
public class OfferDaoTest {
	
	private OfferDao od = new OfferPostgres();
	

	private static MockedStatic<ConnectionUtil> mockedConnectionUtil;
	private static Connection connection;
	
	
	/*
	@Test
	public void newOffer() {
		int expected = 4;
		int actual = 0;
		

		try {
			actual = id.newOffer(30, 2, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(expected, actual);
	}
	*/
	
	@Test
	public void getOffersExists() {
		int expected = 2;
		int actual = 0;
		try {
			actual = od.getOffers(1).size();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getOffersNotExists() {
		int expected = 0;
		int actual = -1;
		try {
			actual = od.getOffers(3).size();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void deleteOfferExist() {
		int expected = 1;
		int actual = 0;
		try {
			actual = od.deleteOffer(2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void deleteOfferNotExist() {
		int expected = 0;
		int actual = -1;
		try {
			actual = od.deleteOffer(5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expected, actual);
	}
	
	@Test
	public void getOfferExists() {
		try {
			assertNotNull(od.getOffer(1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getOfferNotExists() {
		try {
			assertNull(od.getOffer(5));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
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
			RunScript.execute(c, new FileReader("setupOfferDao.sql"));
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
			RunScript.execute(c, new FileReader("teardownOfferDao.sql"));
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


