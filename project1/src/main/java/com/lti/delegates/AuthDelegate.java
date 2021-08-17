package com.lti.delegates;

import java.io.IOException;
import java.io.InputStream;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.exceptions.LoginException;
import com.lti.models.User;
import com.lti.services.AuthService;
import com.lti.services.AuthServiceImpl;

public class AuthDelegate implements Delegatable{
	
	AuthService as = AuthServiceImpl.getAuthService();
	private static Logger log = LogManager.getRootLogger();

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

	@Override
	public void handleGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void handlePut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String pathNext = (String) req.getAttribute("pathNext");
		if (pathNext != null) {
			if (pathNext.equals("login")) {
				
				InputStream request = req.getInputStream();
				User userTemp = new ObjectMapper().readValue(request, User.class);
				String token = null;
				
				try {
					token = as.login(userTemp.getUsername(), userTemp.getPassword());
					log.info("Succesful login with token: " + token);
					res.setStatus(200);
				} catch (NoResultException e) {
					token = "username";
					log.warn("Login attemp with wrong username");
					res.setStatus(400);
				} catch (LoginException e) {
					token = "password";
					log.warn("Login attempt with wrong password");
					res.setStatus(400);
				}
				
				res.addHeader("Authorization", token);
				
			}else if (pathNext.equals("register")) {
				
				InputStream request = req.getInputStream();
				User userTemp = new ObjectMapper().readValue(request, User.class);
				String token = null;
				
				try {
					token = as.register(userTemp.getUsername(), userTemp.getPassword(), userTemp.getFirstName(), userTemp.getLastName(), userTemp.getEmail(), userTemp.getRole().getRole());
					res.setStatus(201);
					log.info("Succesful register with token: " + token);
				} catch (ConstraintViolationException e) {
					token = "username";
					log.warn("Register attemp with bad username");
					res.setStatus(400);
				} catch (LoginException e) {
					token = "role";
					log.warn("Register attemp with bad role");
					res.setStatus(400);
				}
				
				res.addHeader("Authorization", token);
				
			}else {
				res.sendError(400, "Path invalid");
				log.error("Bad path");
			}
		}else {
			res.sendError(400, "Path not found");
			log.error("Bad path");
		}
		
	}

	@Override
	public void handleDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
