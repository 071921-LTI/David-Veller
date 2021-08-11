package com.lti.daos;

import com.lti.models.ReimbStatus;

public interface ReimbursementStatusDao {
	
	int addReimbStatus(String status);
	int deleteReimbStatus(int id);
	ReimbStatus getReimbStatus(String status);
	

}
