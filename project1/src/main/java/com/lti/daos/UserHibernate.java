package com.lti.daos;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.lti.exceptions.NotFoundException;
import com.lti.models.User;
import com.lti.util.HibernateUtil;

public class UserHibernate implements UserDao{

	private static UserDao userHibernate;
	
	private UserHibernate() {}
	
	public static UserDao getUserHibernate() {
		if (userHibernate == null) {
			userHibernate = new UserHibernate();
		}
		return userHibernate;
	}
	
	@Override
	public User addUser(User user) throws ConstraintViolationException{
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			Transaction tx = s.beginTransaction();
			s.save(user);
			tx.commit();
		}
		return user;
	}

	@Override
	public User getUserByUsername(String username) throws NoResultException{
		User user = null;
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			String hql = ("from User as user where user.username = :user");
			TypedQuery<User> nq = s.createQuery(hql, User.class);
			nq.setParameter("user", username);
			user = nq.getSingleResult();
		}
		
		return user;
	}

	@Override
	public void updateUser(User user) throws NotFoundException {
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			if (s.get(User.class, user.getUserId()) == null) {
				throw new NotFoundException();
			} else {
				Transaction tx = s.beginTransaction();
				s.merge(user);
				tx.commit();

			}

		}
		
	}

	@Override
	public List<User> getUserByRole(String role) {
		List<User> users = null;
		try (Session s = HibernateUtil.getSessionFactory().openSession()) {
			String hql = ("from User as user where user.role.role = :role");
			TypedQuery<User> nq = s.createQuery(hql, User.class);
			nq.setParameter("role", role);
			users = nq.getResultList();
		}
		return users;
	}

}
