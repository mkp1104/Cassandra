/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 */

package com.skl.app.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skl.app.web.base.BaseServlet;

	
@SuppressWarnings("serial")
public class LogoutServlet extends BaseServlet {
	
	private static final Logger logger = Logger.getLogger(LogoutServlet.class.getName());
	
	public void doService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
			req.getSession(false).invalidate();
		} catch (Exception ex) {
			//perhaps the session is dead, do nothing as we are signing out anyway!
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(LoginServlet.LOGIN_PAGE);
		try {
			dispatcher.forward(req,resp);
		} catch (ServletException e) {
			logger.severe("Issue in forwarding to servlet " + e.toString());
			e.printStackTrace();
		}
	}
	
}
