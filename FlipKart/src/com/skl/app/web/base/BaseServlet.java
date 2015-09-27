/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 */

package com.skl.app.web.base;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.skl.cassandra.dao.CassandraClient;


@SuppressWarnings("serial")
public abstract class BaseServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doService(req, resp);
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doService(req, resp);
	}
	
	protected CassandraClient getCassandraClient() {
		return (CassandraClient) this.getServletContext().getAttribute(CassandraClient.CASSANDRA_DAO);

	}

	protected abstract void doService(HttpServletRequest req, HttpServletResponse resp) throws IOException;

}
