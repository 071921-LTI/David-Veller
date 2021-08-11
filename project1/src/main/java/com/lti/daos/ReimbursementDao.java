package com.lti.daos;

import java.util.List;

import com.lti.models.Reimb;

public interface ReimbursementDao {

	Reimb addReimb(Reimb reimb);
	List<Reimb> getReimbByStatusAndUser(String status, String username);
	void updateReimb(Reimb reimb);
	List<Reimb> getReimbByStatus(String status);
	List<Reimb> getReimbByUser(String username);
	void deleteReimb(Reimb reimb);
	
}
