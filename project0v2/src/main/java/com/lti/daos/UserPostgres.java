package com.lti.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lti.models.User;
import com.lti.util.ConnectionUtil;

public class UserPostgres implements UserDao {
	
	private static UserDao userPostgres;
	
	private UserPostgres() {}
	
	public static UserDao getUserPostgres() {
		if (userPostgres == null) {
			userPostgres = new UserPostgres();
		}
		return userPostgres;
	}

	@Override
	public User getUserByUsername(String username) throws IOException, SQLException {
		String sql = "select * from users where user_user = ?;";
		User user = null;

		Connection con = ConnectionUtil.getConnectionFromFile();

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, username);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			int id = rs.getInt("user_id");
			String pass = rs.getString("user_pass");
			String role = rs.getString("user_role");
			
			user = new User(id, username, pass, role);
		}

		return user;
	}

	@Override
	public int addUser(String username, String password, String role) throws IOException, SQLException {
		String sql = "insert into users (user_user, user_pass, user_role) values (?, ?, ?) returning user_id";
		int id = -1;
		
		Connection con = ConnectionUtil.getConnectionFromFile();
		
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1,  username);
		ps.setString(2,  password);
		ps.setString(3, role);
		
		
		
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			id = rs.getInt("user_id");
		}
		
		return id;
	}

	@Override
	public User getUserById(int userId) throws IOException, SQLException {
		String sql = "select * from users where user_id = ?;";
		User user = null;

		Connection con = ConnectionUtil.getConnectionFromFile();

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, userId);

		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			String username = rs.getString("user_user");
			String pass = rs.getString("user_pass");
			String role = rs.getString("user_role");
			
			user = new User(userId, username, pass, role);
		}

		return user;
	}

	@Override
	public List<User> getEmployees() throws IOException, SQLException {
		String sql = "select * from users where user_role = 'employee';";
		User user = null;
		List<User> employees = new ArrayList<>();
		
		Connection con = ConnectionUtil.getConnectionFromFile();
		
		PreparedStatement ps = con.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			int id = rs.getInt("user_id");
			String username = rs.getString("user_user");
			
			user = new User(id, username, null, "employee");
			employees.add(user);
			
		}
		
		
		return employees;
	}

	@Override
	public int removeUser(int userId) throws IOException, SQLException {
		String sql = "delete from users where user_id = ?;";
		int rowsChanged = 0;
		
		Connection con = ConnectionUtil.getConnectionFromFile();
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, userId);
		
		rowsChanged = ps.executeUpdate();
		
		return rowsChanged;
	}

}
