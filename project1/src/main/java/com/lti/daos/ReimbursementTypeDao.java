package com.lti.daos;

import com.lti.models.ReimbType;

public interface ReimbursementTypeDao {
	
	int addReimbType(String type);
	int deleteReimbType(int id);
	ReimbType getReimbType(String type);

}
