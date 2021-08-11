package com.revature.daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.Pokemon;
import com.revature.models.Trainer;
import com.revature.util.HibernateUtil;

public class PokemonHibernate implements PokemonDao{

	@Override
	public Pokemon add(Pokemon p) {
		
		try(Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction tx = s.beginTransaction();
			
			s.persist(p);
			tx.commit();
			
		}
		
		return p;
	}

	@Override
	public Pokemon getById(int id) {
		Pokemon p = null;
		try(Session s = HibernateUtil.getSessionFactory().openSession()){
			p = s.get(Pokemon.class, id);
		}
		return p;
	}

	@Override
	public List<Pokemon> getAll() {
		List<Pokemon> pokemons = null;
		try(Session s = HibernateUtil.getSessionFactory().openSession()){
			pokemons = s.createQuery("FROM Pokemon", Pokemon.class).list();
		}
		
		return pokemons;
	}

	@Override
	public void update(Pokemon p) {
		try(Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction tx = s.beginTransaction();
			s.merge(p);
			tx.commit();
		}
		
	}

	@Override
	public void delete(Pokemon p) {
		try(Session s = HibernateUtil.getSessionFactory().openSession()){
			Transaction tx = s.beginTransaction();
			s.delete(p);
			tx.commit();
		}	
		
	}

}
