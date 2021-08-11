package com.lti.daos;

import java.util.List;

import com.lti.exceptions.NotFoundException;
import com.lti.models.Reimb;
import com.lti.models.ReimbStatus;
import com.lti.models.User;

public interface ReimbursementDao {

	Reimb addReimb(Reimb reimb);
	Reimb getReimb(Reimb reimb);
	List<Reimb> getReimbByStatusAndUser(ReimbStatus status, User user);
	void updateReimb(Reimb reimb) throws NotFoundException;
	List<Reimb> getReimbByStatus(String status);
	List<Reimb> getReimbByUser(String username);
	void deleteReimb(Reimb reimb);
	List<Reimb> getReimbByStatusAndUser(String status, String user);
	
}
