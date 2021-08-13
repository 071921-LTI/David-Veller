package com.lti.daos;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.lti.models.Reimb;
import com.lti.models.ReimbStatus;
import com.lti.models.User;
import com.lti.util.HibernateUtil;

public class ReimbursementHibernate implements ReimbursementDao {
	
	private static ReimbursementDao reimbursementHibernate;

	private ReimbursementHibernate() {
	};

	public static ReimbursementDao getReimbursementHibernate() {
		if (reimbursementHibernate == null) {
			reimbursementHibernate = new ReimbursementHibernate();
		}
		return reimbursementHibernate;
	}

	@Override
	public Reimb addReimb(Reimb reimb) {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			Transaction tx = s.beginTransaction();
			s.persist(reimb);
			tx.commit();
		}
		return reimb;
	}

	@Override
	public Reimb getReimb(Reimb reimb) {
		Reimb r = null;
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			r = s.get(Reimb.class, reimb.getReimbId());
		}
		return r;
	}

	@Override
	public List<Reimb> getReimbByStatusAndUser(ReimbStatus status, User user) {
		List<Reimb> reimbs = null;
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			CriteriaBuilder cb = s.getCriteriaBuilder();
			CriteriaQuery<Reimb> cq = cb.createQuery(Reimb.class);
			Root<Reimb> root = cq.from(Reimb.class);

			Predicate predStatus = cb.equal(root.get("status"), status);
			Predicate predUser = cb.equal(root.get("author"), user);

			cq.select(root).where(cb.and(predStatus, predUser));

			reimbs = s.createQuery(cq).list();
		}
		return reimbs;
	}
	
	@Override
	public List<Reimb> getReimbByStatusAndUser(String status, String user) {
		List<Reimb> reimbs = null;
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			String hql = ("from Reimb as reimb where reimb.status.status = :strStatus and reimb.author.username = :user");
			TypedQuery<Reimb> nq = s.createQuery(hql, Reimb.class);
			nq.setParameter("strStatus", status);
			nq.setParameter("user", user);
			reimbs = nq.getResultList();
		}
		return reimbs;
	}

	@Override
	public boolean updateReimb(Reimb reimb) {

		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			if (s.get(Reimb.class, reimb.getReimbId()) == null) {
				return false;
			} else {
				Transaction tx = s.beginTransaction();
				s.merge(reimb);
				tx.commit();

			}
			return true;
		}

	}

	@Override
	public List<Reimb> getReimbByStatus(String status) {
		List<Reimb> reimbs = null;
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			String hql = ("from Reimb as reimb where reimb.status.status = :strStatus");
			TypedQuery<Reimb> nq = s.createQuery(hql, Reimb.class);
			nq.setParameter("strStatus", status);
			reimbs = nq.getResultList();
		}
		
		return reimbs;
	}

	@Override
	public List<Reimb> getReimbByUser(String username) {
		List<Reimb> reimbs = null;
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			String hql = ("from Reimb as reimb where reimb.author.username = :user");
			TypedQuery<Reimb> nq = s.createQuery(hql, Reimb.class);
			nq.setParameter("user", username);
			reimbs = nq.getResultList();
		}
		
		return reimbs;
	}

	@Override
	public void deleteReimb(Reimb reimb) {
		
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			Transaction tx = s.beginTransaction();
			s.delete(reimb);
			tx.commit();
		}

	}

	@Override
	public List<Reimb> getAllReimb() {
		List<Reimb> reimbs = null;
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			reimbs = s.createQuery("FROM Reimb", Reimb.class).getResultList();
		}
		return reimbs;
	}

}
