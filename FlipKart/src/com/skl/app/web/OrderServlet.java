/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
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


@SuppressWarnings("serial")
public class OrderServlet extends BaseServlet {

	private static final Logger logger = Logger.getLogger(OrderServlet.class.getName());

	public void doService(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession(false);
		String userId = (String) session.getAttribute(UserDO.DisplayAttributes.userId.toString());

		//STEP 1: Saving the new order
		OrderDO order = null;
		try {
			String details = req.getParameter(OrderDO.DisplayAttributes.details.toString());
			String amount = req.getParameter(OrderDO.DisplayAttributes.amount.toString());
			String tax = req.getParameter(OrderDO.DisplayAttributes.tax.toString());

			if(StringUtils.isNotEmpty(details) || StringUtils.isNotEmpty(amount) || StringUtils.isNotEmpty(tax)) {
				UserDO user = UserDAO.getUser(getCassandraClient(), userId);
				order = new OrderDO(userId, details, Double.valueOf(amount), Double.valueOf(tax));
				order.setCity(user.getCity());
				OrderDAO.save(getCassandraClient(), order);

				req.setAttribute(OrderDO.DisplayAttributes.orderMsg.toString(), "Order id is " + order.getOrderNum().toString());
				logger.info("Order created " + order.getOrderNum());
			} else {
				req.setAttribute(OrderDO.DisplayAttributes.orderMsg.toString(), "YO! Nothing to work on...");
			}
		} catch(Exception ex) {
			req.setAttribute(OrderDO.DisplayAttributes.orderMsg.toString(), "Error!");
			logger.severe("Oops! Something broke. Figure it based on " + ex.toString());
		}
		
		//STEP 2: Get the past orders (if any) - aaaaah will eventual consistency hit me here, hmmmm? Try fixing the problem without C* CL
		try {
			List<OrderDO> orders = OrderDAO.getOrders(getCassandraClient(), userId, Integer.parseInt(OrderDO.dateFormatYear.format(new Date())));
			req.setAttribute(OrderDO.DisplayAttributes.orderList.toString(), orders);
		} catch(Exception ex) {
			logger.severe("Oops! Something went kaput at the time of getting orders! Figure " + ex.toString());
		}

		//STEP 3: Forward to JSP now
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(LoginServlet.HOME_PAGE);
		try {
			dispatcher.forward(req,resp);
		} catch (ServletException e) {
			logger.severe("Issue in forwarding to servlet " + e.toString());
			e.printStackTrace();
		}
	}

}
