package com.lti.daos;

import java.util.List;

import com.lti.exceptions.IdMissingException;
import com.lti.models.Reimb;

public interface ReimbursementDao {

	int addReimb(Reimb reimb);
	List<Reimb> getReimbByStatusAndUser(String status, int userId);
	int updateReimb(Reimb reimb) throws IdMissingException;
	List<Reimb> getReimbByStatus(String status);
	List<Reimb> getReimbByUser(int userId);
	int deleteReimb(int reimbId);
	
}
