package com.revature;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.revature.daos.PokemonDao;
import com.revature.daos.PokemonHibernate;
import com.revature.daos.TrainerDao;
import com.revature.daos.TrainerHibernate;
import com.revature.models.Pokemon;
import com.revature.models.Trainer;
import com.revature.util.HibernateUtil;

public class Driver {

	static TrainerDao td = new TrainerHibernate();
	static PokemonDao pd = new PokemonHibernate();
	public static void main(String[] args) {
		
		Trainer t = new Trainer("Kevin");
		Pokemon p1 = new Pokemon("mew", t, 151);
		Pokemon p2 = new Pokemon("mewtwo", t, 150);
		
		td.add(t);
		
		
		pd.add(p1);
		pd.add(p2);
		
		//System.out.println(Hibernate.unproxy(pd.getById(2)));
		
		/*
		Session s = HibernateUtil.getSessionFactory().openSession();
		
		Pokemon test = s.load(Pokemon.class, 2);
		
		System.out.println(Hibernate.unproxy(test));
		
		s.close();
		*/
		
		System.out.println(pd.getAll());
		
		p1.setName("bulby");
		
		p1.setId(10);
		
		pd.update(p1);
		
		System.out.println(pd.getAll());
		
		pd.delete(p2);
		
		System.out.println(pd.getAll());
		
	}

}
