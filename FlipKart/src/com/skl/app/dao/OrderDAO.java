/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 */

package com.skl.app.dao;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.skl.app.entity.OrderDO;
import com.skl.cassandra.dao.CassandraClient;

public class OrderDAO {

	//Passing the cassandra client may be a good idea because I could be working on different schemas and/or diff clusters!!
	public static void save(CassandraClient cassandraClient, OrderDO order) {
		PreparedStatement prepStatement = cassandraClient.getSession().prepare(
				"BEGIN BATCH " +
				"  INSERT INTO orders (user_id, year, month, order_num, date, status, amount, tax, details, created_on) values (?,?,?,?,?,?,?,?,?,?); " +
				"  INSERT INTO orders_idx1 (user_id, year, order_num, date) values (?,?,?,?); " + 
				"  INSERT INTO orders_idx2 (user_id, year, month, order_num, city, amount) values (?,?,?,?,?,?); " +
				" APPLY BATCH"
				);
		prepStatement.setConsistencyLevel(ConsistencyLevel.LOCAL_ONE);
		cassandraClient.getSession().execute(prepStatement.bind(
				//Binds for the order table
				order.getUserId(), order.getYear(), order.getMonth(), order.getOrderNum(),
				order.getDate(), order.getStatus(), order.getAmount(), order.getTax(), order.getDetails(), order.getCreatedOn(),
				//Binds for the idx 1 table
				order.getUserId(), order.getYear(), order.getOrderNum(), order.getDate(),
				//Binds for idx 2 table
				order.getUserId(), order.getYear(), order.getMonth(), order.getOrderNum(), order.getCity(), order.getAmount()
				));
	}
	
	
	public static List<OrderDO> getOrders(CassandraClient cassandraClient, String userId, int year) {
		Statement statement = QueryBuilder.select().from("orders")
				.where(QueryBuilder.eq("user_id", userId))
				.and(QueryBuilder.eq("year", year))
				.orderBy(QueryBuilder.desc("order_num"));
		statement.setConsistencyLevel(ConsistencyLevel.LOCAL_ONE);
		statement.setFetchSize(25);		//Get the most recent 25 orders and also helps in paging in batches of 25; UI will show everything
		ResultSet results = cassandraClient.getSession().execute(statement);
		List<Row> rows = (results!=null) ? results.all() : null;

		List<OrderDO> orders = new ArrayList<OrderDO>();
		for(Row row : rows) {
			OrderDO order = new OrderDO(userId, row.getString("details"), row.getDouble("amount"), row.getDouble("tax"), false);
			order.setCreatedOn(row.getDate("created_on"));
			order.setDate(row.getInt("date"));
			order.setOrderNum(row.getUUID("order_num"));
			order.setYear(row.getInt("year"));
			orders.add(order);
		}

		return orders;
	}
	
}
