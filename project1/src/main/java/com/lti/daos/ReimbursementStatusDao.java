package com.lti.daos;

import com.lti.models.ReimbursementStatus;

public interface ReimbursementStatusDao {
	
	int addReimbStatus(String status);
	int deleteReimbStatus(int id);
	ReimbursementStatus getReimbStatus(String status);
	

}
