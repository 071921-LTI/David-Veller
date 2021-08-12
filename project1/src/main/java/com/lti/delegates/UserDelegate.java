package com.lti.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserDelegate implements Delegatable{

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
