package com.lti.services;

import java.util.List;

import com.lti.models.Reimb;
import com.lti.models.ReimbStatus;
import com.lti.models.User;

public interface ReimbursementService {
	
	
	boolean addReimb(Reimb reimb);
	List<Reimb> getReimbByStatusAndUser(ReimbStatus status, User user);
	List<Reimb> getReimbByStatusAndUser(String status, String user);
	boolean updateReimb(Reimb reimb);
	List<Reimb> getReimbByStatus(String status);
	List<Reimb> getReimbByUser(String username);
	List<Reimb> getAllReimb();
	Reimb getReimb(int id);
}
