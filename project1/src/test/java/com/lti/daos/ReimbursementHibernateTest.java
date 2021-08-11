package com.lti.daos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
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
public class ReimbursementHibernateTest {

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

	ReimbursementDao rd = ReimbursementHibernate.getReimbursementHibernate();
	
	ReimbStatus rs = new ReimbStatus(1, "pending");
	ReimbType rt = new ReimbType(1, "LODGING");
	UserRole empl = new UserRole(2, "employee");
	UserRole man = new UserRole(1, "manager");
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
	public void addReimb() {
		Reimb expected = reimb;
		Reimb result = rd.addReimb(reimb);
		expected.setReimbId(3);
		assertEquals(expected, result);
	}
	
	@Test
	@Order(3)
	public void getReimbByStatusAndUser1() {
		assertEquals(3, rd.getReimbByStatusAndUser(rs, emplUser).size());
	}
	
	@Test
	@Order(4)
	public void getReimbByStatusAndUser2() {
		assertEquals(0, rd.getReimbByStatusAndUser(rs, manUser).size());
	}
	
	@Test
	@Order(5)
	public void getReimbByStatusAndUser3() {
		assertEquals(0, rd.getReimbByStatusAndUser(new ReimbStatus(2, "resolved"), emplUser).size());
	}
	
	@Test
	@Order(6)
	public void getReimbByStatusAndUser1str() {
		assertEquals(3, rd.getReimbByStatusAndUser("pending", "username").size());
	}
	
	@Test
	@Order(7)
	public void getReimbByStatusAndUser2str() {
		assertEquals(0, rd.getReimbByStatusAndUser("pending", "david").size());
	}
	
	@Test
	@Order(8)
	public void getReimbByStatusAndUser3str() {
		assertEquals(0, rd.getReimbByStatusAndUser("resolved", "username").size());
	}
	
	@Test
	@Order(14)
	public void updateReimb() throws NotFoundException {
		reimb.setReimbId(3);
		reimb = rd.getReimb(reimb);
		reimb.setAuthor(manUser);
		reimb.setResolver(manUser);
		rd.updateReimb(reimb);
		assertEquals(rd.getReimb(reimb).getReimbId(), reimb.getReimbId());
	}
	
	@Test
	@Order(10)
	public void updateReimbNoId() {
		Reimb testReimb = reimb;
		testReimb.setReimbId(10);
		assertThrows(NotFoundException.class, () -> rd.updateReimb(testReimb));
	}
	
	@Test
	@Order(11)
	public void getReimbByStatusExists() {
		assertEquals(3, rd.getReimbByStatus("pending").size());
	}
	
	@Test
	@Order(12)
	public void getReimbByStatusNotExists() {
		assertEquals(0, rd.getReimbByStatus("resolved").size());
	}
	
	@Test
	@Order(13)
	public void getReimbByUserExists() {
		assertEquals(3, rd.getReimbByUser("username").size());
	}
	
	@Test
	@Order(9)
	public void getReimbByUserNotExists() {
		assertEquals(0, rd.getReimbByUser("david").size());
	}
	
	@Test
	@Order(15)
	public void deleteReimbExists() {
		rd.deleteReimb(reimb);
		assertEquals(null, rd.getReimb(reimb));
	}
	
}
