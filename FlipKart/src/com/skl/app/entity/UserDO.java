/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 */

package com.skl.app.entity;

import java.util.Date;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "flipbasket", name = "users")
public class UserDO {

	public enum FetchAttributes {
		loginId, password
	}

	public enum DisplayAttributes {
		userId, firstName, lastName
	}

	@PartitionKey(value=0) @Column(name="user_id") private String userId;
	private String password;
	@Column(name="first_name") private String firstName;
	@Column(name="last_name") private String lastName;
	@Column(name="address_1") private String address1;
	@Column(name="address_2") private String address2;
	private String city;
	private String phone;
	@Column(name="created_on") private Date createdOn;
	@Column(name="modified_on") private Date modifiedOn;


	public UserDO(String userId) {
		this.userId = userId;
		this.createdOn = this.modifiedOn = new Date();
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getAddress1() {
		return address1;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public String getAddress2() {
		return address2;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public Date getModifiedOn() {
		return modifiedOn;
	}


	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}


	public String getUserId() {
		return userId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
