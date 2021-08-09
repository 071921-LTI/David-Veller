package com.lti.daos;

import com.lti.models.ReimbursementType;

public interface ReimbursementTypeDao {
	
	int addReimbType(String type);
	int deleteReimbType(int id);
	ReimbursementType getReimbType(String type);

}
