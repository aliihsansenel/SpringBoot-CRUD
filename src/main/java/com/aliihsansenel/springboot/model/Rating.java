package com.aliihsansenel.springboot.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Rating extends Model {
	public int customerId = -1;	
	public int itemId = -1;
	public float rating = -1F;
	
	@Override
	public Model toModel(ResultSet rs) {
		try {
			customerId = rs.getInt(1);
			itemId = rs.getInt(2);
			rating = rs.getFloat(3);
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

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
}
