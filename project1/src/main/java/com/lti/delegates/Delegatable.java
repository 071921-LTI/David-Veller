package com.lti.delegates;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Delegatable {

	void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;

	void handleGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;

	void handlePut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;

	void handlePost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;

	void handleDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;

}
