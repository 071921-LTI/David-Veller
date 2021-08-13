package com.lti.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lti.delegates.AuthDelegate;
import com.lti.delegates.ReimbursementDelegate;
import com.lti.delegates.UserDelegate;

public class RequestHelper {
	
	private UserDelegate ud = new UserDelegate();
	private AuthDelegate ad = new AuthDelegate();
	private ReimbursementDelegate rd = new ReimbursementDelegate();

	public void process(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		String path = req.getPathInfo();

		if (path != null && !path.equals("/")) {

			path = path.substring(1);

			if (path.indexOf("/") != -1) {
				String[] paths = path.split("/", 2);
				path = paths[0];
				req.setAttribute("pathNext", paths[1]);
			}

			switch (path) {
			case "user":
				ud.process(req, res);
				break;
			case "auth":
				ad.process(req, res);
				break;
			case "reimb":
				rd.process(req, res);
				break;
			default:
				res.sendError(400, "Path not supported " + path);
			}

		} else {
			res.sendError(400, "No path found");
		}

	}

}
