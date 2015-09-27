/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 * 
 * Here is a little sample of providing a feature of updating a user password
  		Statement update = QueryBuilder.update("flipbasket", "users")
				.with(QueryBuilder.set("password", newPwd))
				.where((QueryBuilder.eq("user_id", uid)));
        session.execute(update);
        
   Here is how you can remove the user (not an ideal use case)
        Statement delete = QueryBuilder.delete().from("flipbasket", "users")
				.where(QueryBuilder.eq("user_id", uid));
		session.execute(delete);
 * 
 */

package com.skl.app.dao;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.skl.app.entity.UserDO;
import com.skl.cassandra.dao.CassandraClient;

public class UserDAO {

	public static UserDO getUser(CassandraClient cassandraClient, String loginId) {
		Statement statement = QueryBuilder.select().from("users").where(QueryBuilder.eq("user_id", loginId));
		statement.setConsistencyLevel(ConsistencyLevel.LOCAL_ONE);
		ResultSet results = cassandraClient.getSession().execute(statement);

		Row row = results!=null ? results.one() : null;
		UserDO user = null;
		if(row!=null) {
			user = new UserDO(row.getString("user_id"));
			user.setPassword(row.getString("password"));
			user.setFirstName(row.getString("first_name"));
			user.setLastName(row.getString("last_name"));
			user.setAddress1(row.getString("address_1"));
			user.setAddress1(row.getString("address_2"));
			user.setCity(row.getString("city"));
			user.setPhone(row.getString("phone"));
		}
		
		return user;
	}
	
}

