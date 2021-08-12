package com.lti.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lti.daos.ReimbursementDao;
import com.lti.models.Reimb;
import com.lti.models.ReimbStatus;
import com.lti.models.ReimbType;
import com.lti.models.User;
import com.lti.models.UserRole;

@ExtendWith(MockitoExtension.class)
public class ReimbursementServiceTest {
	
	ReimbType rt = new ReimbType(1, "LODGING");
	ReimbStatus rs = new ReimbStatus(1, "pending");
	UserRole empl = new UserRole(2, "employee");
	User user = new User("newUser", "password", "first", "last", "newuser@email.com", empl);
	Reimb reimb = new Reimb(30, Timestamp.valueOf(LocalDateTime.now()), user, rs, rt);
	
	@Mock
	ReimbursementDao rd;
	
	@InjectMocks
	ReimbursementService reimbService = ReimbursementServiceImpl.getReimbursementService();
	
	@Test
	public void addReimbTrue() {
		Mockito.when(rd.addReimb(reimb)).thenReturn(reimb);
		assertEquals(reimbService.addReimb(reimb), true);	
	}
	
	@Test
	public void addReimbFalse() {
		Mockito.when(rd.addReimb(reimb)).thenReturn(null);
		assertEquals(reimbService.addReimb(reimb), false);
	}
	
	@Test
	public void getReimbByStatusAndUserObject() {
		List<Reimb> reimbs = new ArrayList<>();
		reimbs.add(reimb);
		Mockito.when(rd.getReimbByStatusAndUser(rs, user)).thenReturn(reimbs);
		assertEquals(reimbs, reimbService.getReimbByStatusAndUser(rs, user));
	}
	
	@Test
	public void getReimbByStatusAndUserStr() {
		List<Reimb> reimbs = new ArrayList<>();
		reimbs.add(reimb);
		Mockito.when(rd.getReimbByStatusAndUser("pending", "newUser")).thenReturn(reimbs);
		assertEquals(reimbs, reimbService.getReimbByStatusAndUser("pending", "newUser"));
	}
	
	@Test
	public void updateReimbExist() {
		Mockito.when(rd.updateReimb(reimb)).thenReturn(true);
		assertEquals(true, reimbService.updateReimb(reimb));
	}
	
	@Test
	public void updateReimbNotExist() {
		Mockito.when(rd.updateReimb(reimb)).thenReturn(false);
		assertEquals(false, reimbService.updateReimb(reimb));
	}
	
	@Test
	public void getReimbByStatus() {
		List<Reimb> reimbs = new ArrayList<>();
		reimbs.add(reimb);
		Mockito.when(rd.getReimbByStatus("pending")).thenReturn(reimbs);
		assertEquals(reimbs, reimbService.getReimbByStatus("pending"));
	}
	
	@Test
	public void getReimbByUser() {
		List<Reimb> reimbs = new ArrayList<>();
		reimbs.add(reimb);
		Mockito.when(rd.getReimbByUser("newUser")).thenReturn(reimbs);
		assertEquals(reimbs, reimbService.getReimbByUser("newUser"));
	}

}
