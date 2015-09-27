/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 * 
 */

package com.skl.app.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.skl.app.dao.OrderDAO;
import com.skl.app.dao.UserDAO;
import com.skl.app.entity.OrderDO;
import com.skl.app.entity.UserDO;
import com.skl.app.web.base.BaseServlet;
import com.skl.cassandra.dao.CassandraClient;

	
@SuppressWarnings("serial")
public class LoginServlet extends BaseServlet {
	
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
	
	public static final String HOME_PAGE = "/home.jsp";
	public static final String LOGIN_PAGE = "/index.jsp";

	public void doService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String forwardJsp = "";
		String loginId = req.getParameter(UserDO.FetchAttributes.loginId.toString());
		String password = req.getParameter(UserDO.FetchAttributes.password.toString());

		CassandraClient cassandraClient = (CassandraClient) this.getServletContext().getAttribute(CassandraClient.CASSANDRA_DAO);
		UserDO user	= UserDAO.getUser(cassandraClient, loginId);
		if(user!=null && StringUtils.equals(password, user.getPassword())) {
			HttpSession session = req.getSession(true);
			session.setAttribute(UserDO.DisplayAttributes.userId.toString(), user.getUserId());
			session.setAttribute(UserDO.DisplayAttributes.firstName.toString(), user.getFirstName());
			session.setAttribute(UserDO.DisplayAttributes.lastName.toString(), user.getLastName());
			
			try {
				List<OrderDO> orders = OrderDAO.getOrders(getCassandraClient(), loginId, Integer.parseInt(OrderDO.dateFormatYear.format(new Date())));
				req.setAttribute(OrderDO.DisplayAttributes.orderList.toString(), orders);
			} catch(Exception ex) {
				logger.severe("Oops! Something went kaput at the time of getting orders! Figure this -> " + ex.toString());
			}

			forwardJsp = HOME_PAGE;
			logger.info("Login was good.");
		} else {
			forwardJsp = LOGIN_PAGE;
			logger.info("Login was NOT good.");
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(forwardJsp);
		try {
			dispatcher.forward(req,resp);
		} catch (ServletException e) {
			logger.severe("Issue in forwarding to servlet " + e.toString());
			e.printStackTrace();
		}
	}
	
}
