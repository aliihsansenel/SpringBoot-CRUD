package com.aliihsansenel.springboot.model;

import java.sql.ResultSet;
import java.sql.SQLException;
 
public class Item extends Model {

	public int itemId;
	public String itemName;	
	public String supplier;
	public int stockQuantity = -1;
	public float price = -1.0F;
	
	@Override
	public Model toModel(ResultSet rs) {
		try {
			itemId = rs.getInt(1);
			itemName = rs.getString(2);
			supplier = rs.getString(3);
			stockQuantity = rs.getInt(4);
			price = rs.getFloat(5);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return this;
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

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
