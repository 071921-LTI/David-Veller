package com.lti.delegates;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.models.Reimb;
import com.lti.models.User;
import com.lti.services.AuthService;
import com.lti.services.AuthServiceImpl;
import com.lti.services.ReimbursementService;
import com.lti.services.ReimbursementServiceImpl;
import com.lti.services.UserService;
import com.lti.services.UserServiceImpl;

public class ReimbursementDelegate implements Delegatable {

	ReimbursementService rs = ReimbursementServiceImpl.getReimbursementService();
	AuthService as = AuthServiceImpl.getAuthService();
	UserService us = UserServiceImpl.getUserService();

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
		String token = req.getHeader("Authorization");
		String username = as.authorize(token);
		User user = us.getUserByUsername(username);
		if (user != null) {
			String pathNext = (String) req.getAttribute("pathNext");
			if (pathNext != null) {
				//checks path for /view or /view/reimbId
				if (pathNext.indexOf("/") == -1 && pathNext.equals("view")) {
					
					List<Reimb> reimbs;
					
					if (user.getRole().getRole().equals("manager")) {
						reimbs = rs.getAllReimb();
					}else {
						reimbs = rs.getReimbByUser(user.getUsername());
					}
					
					res.setStatus(200);
					res.addHeader("Authorization", token);
					try (PrintWriter pw = res.getWriter()) {
						pw.write(new ObjectMapper().writeValueAsString(reimbs));
					}
					
				} else {
					String[] path = pathNext.split("/");
					if (!path[0].equals("view")) {
						res.sendError(400, "Path Error");
					} else {
						int reimbId = Integer.valueOf(path[1]);
						Reimb reimb = rs.getReimb(reimbId);
						res.setStatus(200);
						res.addHeader("Authorization", token);
						try (PrintWriter pw = res.getWriter()) {
							pw.write(new ObjectMapper().writeValueAsString(reimb));
						}
					}
				}
			} else {
				res.sendError(400, "Path not found");
			}
		}else {
			res.sendError(400, "Bad token");
		}

	}

	@Override
	public void handlePut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void handlePost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
