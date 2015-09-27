/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 */

package com.skl.app.web;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.skl.app.entity.UserDO;
import com.skl.cassandra.dao.CassandraClient;


@SuppressWarnings("serial")
public class CreateUserServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		CassandraClient cassandraClient = (CassandraClient) this.getServletContext().getAttribute(CassandraClient.CASSANDRA_DAO);
		MappingManager manager = new MappingManager(cassandraClient.getSession());
		Mapper<UserDO> mapper = manager.mapper(UserDO.class);
		
		for(int i=1; i<=30; i++) {
			UserDO user = new UserDO("u"+i);
			user.setPassword("cassandra");
			user.setFirstName("User " + i);
			user.setAddress1("Starfleet Academy Residency #" + i);
			user.setAddress2("3rd Rock from Sun");
			user.setCity("Fort Baker");
			user.setPhone("123456789" + i);
    		mapper.save(user);
		//	mapper.saveAsync(user);
		}
		
		resp.setContentType("text/plain");
		resp.getWriter().println("Users created ...");
	}

}
