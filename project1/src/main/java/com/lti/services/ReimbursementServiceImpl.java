package com.lti.services;

import java.util.List;

import com.lti.daos.ReimbursementDao;
import com.lti.daos.ReimbursementHibernate;
import com.lti.models.Reimb;
import com.lti.models.ReimbStatus;
import com.lti.models.User;

public class ReimbursementServiceImpl implements ReimbursementService{
	
	private static ReimbursementService reimbursementServiceImpl;
	
	private ReimbursementServiceImpl() {}
	
	public static ReimbursementService getReimbursementService() {
		if (reimbursementServiceImpl == null) {
			reimbursementServiceImpl = new ReimbursementServiceImpl();
		}
		return reimbursementServiceImpl;
	}
	
	ReimbursementDao rd = ReimbursementHibernate.getReimbursementHibernate();

	@Override
	public boolean addReimb(Reimb reimb) {
		if (rd.addReimb(reimb) != null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public List<Reimb> getReimbByStatusAndUser(ReimbStatus status, User user) {
		return rd.getReimbByStatusAndUser(status, user);
	}

	@Override
	public List<Reimb> getReimbByStatusAndUser(String status, String user) {
		return rd.getReimbByStatusAndUser(status, user);
	}

	@Override
	public boolean updateReimb(Reimb reimb) {
		return rd.updateReimb(reimb);
	}

	@Override
	public List<Reimb> getReimbByStatus(String status) {
		return rd.getReimbByStatus(status);
	}

	@Override
	public List<Reimb> getReimbByUser(String username) {
		return rd.getReimbByUser(username);
	}

	@Override
	public List<Reimb> getAllReimb() {
		return rd.getAllReimb();
	}

	@Override
	public Reimb getReimb(int id) {
		return rd.getReimb(new Reimb(id));
	}

}
