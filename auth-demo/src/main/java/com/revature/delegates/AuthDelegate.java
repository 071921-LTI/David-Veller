package com.revature.delegates;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.AuthServiceImpl;

public class AuthDelegate implements Delegatable{
	
	AuthService as = new AuthServiceImpl();

	@Override
	public void process(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		String method = rq.getMethod();

		switch (method) {
		case "GET":
			handleGet(rq, rs);
			break;
		case "POST":
			handlePost(rq, rs);
			break;
		case "PUT":
			handlePut(rq, rs);
			break;
		case "DELETE":
			handleDelete(rq, rs);
			break;
		default:
			rs.sendError(405);
		}
		
	}

	@Override
	public void handleGet(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		
		
	}

	@Override
	public void handlePut(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handlePost(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		String pathNext = (String) rq.getAttribute("pathNext");
		
		if (pathNext == null) {
			rs.sendError(404, "Path not found");
		}else if (pathNext.equals("login")) {
			
			InputStream request = rq.getInputStream();
			// Converts the request body into a User.class object
			User userTemp = new ObjectMapper().readValue(request, User.class);
			User user = null;
			
			try {
				user = as.login(userTemp.getUsername(), userTemp.getPassword());
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (user == null) {
				rs.sendError(404, "User not found");
			}else {
				String token = as.createToken(user);
				rs.setStatus(200);
				rs.addHeader("Authorize", token);
			}
			
		}
		
	}

	@Override
	public void handleDelete(HttpServletRequest rq, HttpServletResponse rs) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
