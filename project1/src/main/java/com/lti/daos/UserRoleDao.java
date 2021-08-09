package com.lti.daos;

import com.lti.models.UserRole;

public interface UserRoleDao {
	
	int addUserRole(String role);
	int deleteUserRole(int id);
	UserRole getUserRole(String role);

}
