/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 */

package com.skl.app.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.Transient;

@Table(keyspace = "flipbasket", name = "orders")
public class OrderDO {
	
	public enum DisplayAttributes {
		details, amount, tax, orderNum, orderList, orderMsg
	}

	@PartitionKey(value=0) @Column(name="user_id") private String userId;
	@PartitionKey(value=1) private int year;
	private int month;
	@Column(name="order_num") private UUID orderNum;
	private int date;	//yyyymmdd as int
	private int status;
	private double amount;
	private double tax;
	private String details;
	@Column(name="created_on") private Date createdOn;
	
	@Transient private String city;

	@Transient public static SimpleDateFormat dateFormatYear = new SimpleDateFormat("YYYY");
	@Transient public static SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MM");
	@Transient public static SimpleDateFormat dateFormatDay = new SimpleDateFormat("DD");


	public OrderDO(String userId, String details, double amount, double tax) {
		this(userId, details, amount, tax, true);
	}
	
	public OrderDO(String userId, String details, double amount, double tax, boolean autoFill) {
		this.userId = userId;
		this.details = details;
		this.amount = amount;
		this.tax = tax;
		this.status = 1;

		if(autoFill) {
			this.createdOn = new Date();
			this.year = Integer.parseInt(dateFormatYear.format(createdOn));
			this.month = Integer.parseInt(dateFormatMonth.format(createdOn));
			this.date = year*10000 + month*100 + Integer.parseInt(dateFormatDay.format(createdOn));
			this.orderNum = UUIDs.timeBased();
		}
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setOrderNum(UUID orderNum) {
		this.orderNum = orderNum;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUserId() {
		return userId;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public UUID getOrderNum() {
		return orderNum;
	}

	public int getDate() {
		return date;
	}

	public int getStatus() {
		return status;
	}

	public double getAmount() {
		return amount;
	}

	public double getTax() {
		return tax;
	}

	public String getDetails() {
		return details;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
