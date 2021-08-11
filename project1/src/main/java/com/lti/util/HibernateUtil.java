package com.lti.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	
private static SessionFactory sf;
	
	public static SessionFactory getSessionFactory() {
		if(sf == null || sf.isClosed() == true) {
			Configuration cg = new Configuration();
			cg.setProperty("hibernate.connection.url", System.getenv("DB_URL"));
			cg.setProperty("hibernate.connection.username", System.getenv("DB_USER"));
			cg.setProperty("hibernate.connection.password", System.getenv("DB_PASS"));
			sf = cg.configure("hibernate.cfg.xml").buildSessionFactory();
		}
		
		return sf;
	}

}
