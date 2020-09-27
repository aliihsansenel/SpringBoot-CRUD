package com.aliihsansenel.springboot.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingsJoined extends Model {
	
	public int customerId = -1;
	public String firstname;
	public String sirname;
	public int itemId = -1;
	public String itemName;
	public float rating = -1F;
	
	@Override
	public Model toModel(ResultSet rs) {
		try {
			customerId = rs.getInt(1);
			firstname = rs.getString(2);
			sirname = rs.getString(3);
			itemId = rs.getInt(4);
			itemName = rs.getString(5);
			rating = rs.getFloat(6);
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

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

}
