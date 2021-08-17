package com.lti.delegates;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.models.User;
import com.lti.services.AuthService;
import com.lti.services.AuthServiceImpl;
import com.lti.services.UserService;
import com.lti.services.UserServiceImpl;

public class UserDelegate implements Delegatable {

	@Override
	public void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String method = req.getMethod();

		switch (method) {
		case "GET":
			handleGet(req, res);
			break;
		case "POST":
			handlePost(req, res);
			break;
		case "PUT":
			handlePut(req, res);
			break;
		case "DELETE":
			handleDelete(req, res);
			break;
		default:
			res.sendError(405);
		}

	}

	AuthService as = AuthServiceImpl.getAuthService();
	UserService us = UserServiceImpl.getUserService();
	private static Logger log = LogManager.getRootLogger();

	@Override
	public void handleGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String token = req.getHeader("Authorization");
		String username = as.authorize(token);
		User user = us.getUserByUsername(username);
		if (user != null) {
			String pathNext = (String) req.getAttribute("pathNext");
			if (pathNext != null) {
				if (pathNext.indexOf("/") == -1 && pathNext.equals("prefs")) {

					res.setStatus(200);
					try (PrintWriter pw = res.getWriter()) {
						pw.write(new ObjectMapper().writeValueAsString(user));
					}
					log.info("User "+user.getUsername()+" retrieved");

				} else if(pathNext.indexOf("/") == -1 && pathNext.equals("employees")){
					List<User> employees = us.getUserByRole("employee");
					res.setStatus(200);
					try (PrintWriter pw = res.getWriter()) {
						pw.write(new ObjectMapper().writeValueAsString(employees));
					}
					
				} else {
				
					
					res.sendError(400, "Path not found");
					log.error("Bad path");
				}
			} else {
				res.sendError(400, "Path not found");
				log.error("Bad path");
			}
		} else {
			res.sendError(400, "Bad token");
			log.error("Bad token");
		}
		
	}

	@Override
	public void handlePut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String token = req.getHeader("Authorization");
		String username = as.authorize(token);
		User user = us.getUserByUsername(username);
		if (user != null) {
			String pathNext = (String) req.getAttribute("pathNext");
			if (pathNext != null) {
				if (pathNext.indexOf("/") == -1 && pathNext.equals("prefs")) {

					InputStream request = req.getInputStream();
					User userTemp = new ObjectMapper().readValue(request, User.class);

					userTemp.setUserId(user.getUserId());
					userTemp.setRole(user.getRole());
					if (us.updateUser(userTemp)) {
						log.info("User " + userTemp.getUsername() + " updated.");
						res.setStatus(200);
						token = AuthServiceImpl.tokenize(userTemp);
						res.addHeader("Authorization", token);
					} else {
						res.sendError(400, "Update failed");
						log.error("Update failed");
					}

				} else {

					res.sendError(400, "Path not found");
					log.error("Bad path");
				}
			} else {
				res.sendError(400, "Path not found");
				log.error("Bad path");
			}
		} else {
			res.sendError(400, "Bad token");
			log.error("Bad token");
		}

	}

	@Override
	public void handlePost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

	}

	@Override
	public void handleDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
