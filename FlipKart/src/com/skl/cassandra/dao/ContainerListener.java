/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 */

package com.skl.cassandra.dao;

import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ContainerListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(ContainerListener.class.getName());

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			//Get the parameters from the configuration (can be anything)
			String contactPoints = event.getServletContext().getInitParameter("contact_points");
			String uid = event.getServletContext().getInitParameter("c_uid");
			String pwd = event.getServletContext().getInitParameter("c_pwd");
			String schema = event.getServletContext().getInitParameter("schema");
			
			//Initialize the DAO, in case you have >1 cluster, you will need more than one DAO
			CassandraClient dao = new CassandraClient(contactPoints, schema, uid, pwd);
			ServletContext context = event.getServletContext();
			context.setAttribute(CassandraClient.CASSANDRA_DAO, dao);
			logger.info("Bootstrap finished, C* session initialized");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		CassandraClient client = (CassandraClient) event.getServletContext().getAttribute(CassandraClient.CASSANDRA_DAO);
		if(client!=null) {
			client.destroy();
		}
	}

}
