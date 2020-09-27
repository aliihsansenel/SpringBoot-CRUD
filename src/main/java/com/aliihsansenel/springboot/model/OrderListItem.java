package com.aliihsansenel.springboot.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderListItem extends Model {

	public int customerId;
	public int orderId;

	@Override
	public Model toModel(ResultSet rs) {
		try {
			customerId = rs.getInt(1);
			orderId = rs.getInt(2);
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

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

}
