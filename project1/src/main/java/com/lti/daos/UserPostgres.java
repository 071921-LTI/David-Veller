package com.lti.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lti.exceptions.IdMissingException;
import com.lti.models.User;
import com.lti.util.ConnectionUtil;

public class UserPostgres implements UserDao {

	private static UserDao userPostgres;

	private UserPostgres() {
	};

	public static UserDao getUserPostgres() {
		if (userPostgres == null) {
			userPostgres = new UserPostgres();
		}
		return userPostgres;
	}

	@Override
	public int addUser(User user) throws SQLException {
		int userId = -1;
		String sql = "insert into ers_users (ers_username, ers_password, user_first_name,  "
				+ "user_last_name, user_email, user_role_id) values (?, ?, ?, ?, ?, ?);";

		Connection con = ConnectionUtil.getConnectionFromFile();

		PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		ps.setString(1, user.getUsername());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getFirstName());
		ps.setString(4, user.getLastName());
		ps.setString(5, user.getEmail());
		ps.setInt(6, user.getRoleId());

		ps.execute();

		ResultSet rs = ps.getGeneratedKeys();

		if (rs.next()) {
			userId = rs.getInt("ers_user_id");
		}

		return userId;
	}

	@Override
	public User getUserByUsername(String username) {
		User user = null;
		String sql = "select * from ers_users where ers_username = ?;";

		try (Connection con = ConnectionUtil.getConnectionFromFile()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int ers_user_id = rs.getInt("ers_user_id");
				String ers_username = rs.getString("ers_username");
				String ers_password = rs.getString("ers_password");
				String user_first_name = rs.getString("user_first_name");
				String user_last_name = rs.getString("user_last_name");
				String user_email = rs.getString("user_email");
				int user_role_id = rs.getInt("user_role_id");

				user = new User(ers_user_id, ers_username, ers_password, user_first_name, user_last_name, user_email,
						user_role_id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public User getUserById(int userId) {
		User user = null;
		String sql = "select * from ers_users where ers_user_id = ?;";

		try (Connection con = ConnectionUtil.getConnectionFromFile()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				int ers_user_id = rs.getInt("ers_user_id");
				String ers_username = rs.getString("ers_username");
				String ers_password = rs.getString("ers_password");
				String user_first_name = rs.getString("user_first_name");
				String user_last_name = rs.getString("user_last_name");
				String user_email = rs.getString("user_email");
				int user_role_id = rs.getInt("user_role_id");

				user = new User(ers_user_id, ers_username, ers_password, user_first_name, user_last_name, user_email,
						user_role_id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public int updateUser(User user) throws IdMissingException {
		if (user.getUserId() == 0) {
			throw new IdMissingException();
		}

		int rowsChanged = 0;
		String sql = "update ers_users set ers_username = ?, ers_password = ?, user_first_name = ?, "
				+ "user_last_name = ?, user_email = ?, user_role_id = ? where ers_user_id = ?;";

		try (Connection con = ConnectionUtil.getConnectionFromFile()) {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getEmail());
			ps.setInt(6, user.getRoleId());
			ps.setInt(7, user.getUserId());

			rowsChanged = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowsChanged;
	}

	@Override
	public List<User> getUserByRole(String role) {
		List<User> users = new ArrayList<>();
		String sql = "select * from ers_users where user_role_id in (select ers_user_role_id from ers_user_roles where user_role = ?);";

		try (Connection con = ConnectionUtil.getConnectionFromFile()) {

			PreparedStatement ps = con.prepareStatement(sql);

			ps.setString(1, role);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int ers_user_id = rs.getInt("ers_user_id");
				String ers_username = rs.getString("ers_username");
				String ers_password = rs.getString("ers_password");
				String user_first_name = rs.getString("user_first_name");
				String user_last_name = rs.getString("user_last_name");
				String user_email = rs.getString("user_email");
				int user_role_id = rs.getInt("user_role_id");

				User user = new User(ers_user_id, ers_username, ers_password, user_first_name, user_last_name,
						user_email, user_role_id);

				users.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	/*
	 * @Override public int deleteUser(int userId) { int rowsChanged = 0; String sql
	 * = "delete from ers_users where ers_user_id = ?;";
	 * 
	 * try (Connection con = ConnectionUtil.getConnectionFromFile()){
	 * PreparedStatement ps = con.prepareStatement(sql);
	 * 
	 * ps.setInt(1, userId);
	 * 
	 * rowsChanged = ps.executeUpdate(); }catch(SQLException e) {
	 * e.printStackTrace(); }
	 * 
	 * 
	 * return rowsChanged; }
	 */
}
