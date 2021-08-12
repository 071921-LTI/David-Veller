package com.lti.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FrontController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestHelper rh = new RequestHelper();

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		addCorsHeader(req.getRequestURI(), res);
		rh.process(req, res);
	}

	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		doGet(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		doGet(req, res);
	}

	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		doGet(req, res);
	}

	protected void doOptions(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		addCorsHeader(req.getRequestURI(), res);
		super.doOptions(req, res);
	}
	
	public static void addCorsHeader(String requestURI, HttpServletResponse res) {
		res.addHeader("Access-Control-Allow-Origin", "*");
		res.addHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
		res.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorize");
		res.addHeader("Access-Control-Expose-Headers", "Content-Type, Accept, Authorize");
	}

}
