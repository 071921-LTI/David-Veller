package com.lti.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.lti.daos.UserDao;
import com.lti.daos.UserPostgres;
import com.lti.exceptions.AuthException;
import com.lti.exceptions.UserNotFoundException;
import com.lti.models.User;

public class UserServiceImpl implements UserService{
	
	private static UserService userService;
	
	private UserServiceImpl() {}
	
	public static UserService getUserService() {
		if (userService == null) {
			userService = new UserServiceImpl();
		}
		return userService;
	}
	
	UserDao ud = UserPostgres.getUserPostgres();

	@Override
	public User login(String username, String password) throws IOException, SQLException, AuthException, UserNotFoundException {
		// TODO Auto-generated method stub
		User user = ud.getUserByUsername(username);
		
		if (user == null) {
			throw new UserNotFoundException();
		}
		
		if (!user.getPass().equals(HashPass.getHasher().hashPass(password))) {
			throw new AuthException();
		}
		
		return user;
	}

	@Override
	public User registerCustomer(String username, String password) throws AuthException, IOException, SQLException {
		if (password.length() <= 5) {
			throw new AuthException();
		}
		User user = new User();
		String hashedPass = HashPass.getHasher().hashPass(password);
		user.setId(ud.addUser(username, hashedPass, "customer"));
		user.setUser(username);
		user.setPass(hashedPass);
		user.setRole("customer");
		return user;
	}

	@Override
	public User registerEmployee(String username, String password) throws AuthException, IOException, SQLException {
		if (password.length() <= 5) {
			throw new AuthException();
		}
		User user = new User();
		String hashedPass = HashPass.getHasher().hashPass(password);
		user.setId(ud.addUser(username, hashedPass, "employee"));
		user.setUser(username);
		user.setPass(hashedPass);
		user.setRole("employee");
		return user;
	}

	@Override
	public String getUsernameById(int id) throws AuthException, IOException, SQLException {
		
		return ud.getUserById(id).getUser();
	}

	@Override
	public int fireEmployee(int emplId) throws AuthException, IOException, SQLException {
		// TODO Auto-generated method stub
		return ud.removeUser(emplId);
	}

	@Override
	public List<User> getEmployees() throws AuthException, IOException, SQLException {
		return ud.getEmployees();
	}

	@Override
	public User registerManager(String username, String password) throws AuthException, IOException, SQLException {
		if (password.length() <= 5) {
			throw new AuthException();
		}
		User user = new User();
		String hashedPass = HashPass.getHasher().hashPass(password);
		user.setId(ud.addUser(username, hashedPass, "manager"));
		user.setUser(username);
		user.setPass(hashedPass);
		user.setRole("manager");
		return user;
	}

}
