package com.aliihsansenel.springboot.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Customer extends Model{

	public int customerId;
	public String firstname;
	public String sirname;
	public String address;


	@Override
	public Model toModel(ResultSet rs) {
		try {
			customerId = rs.getInt(1);
			firstname = rs.getString(2);
			sirname = rs.getString(3);
			address = rs.getString(4);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return this;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSirname() {
		return sirname;
	}

	public void setSirname(String sirname) {
		this.sirname = sirname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}