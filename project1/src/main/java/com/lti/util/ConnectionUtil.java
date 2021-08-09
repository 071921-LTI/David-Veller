package com.lti.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
	
	private static Connection con;
	
	public static Connection getConnectionFromFile() throws SQLException {
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try {
			prop.load(loader.getResourceAsStream("prop.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = prop.getProperty("url");
		String username = prop.getProperty("user");
		String password = prop.getProperty("pass");
		
		if(con == null || con.isClosed()) {
			con = DriverManager.getConnection(url, username, password);
		} 
		
		return con;
	}

}
