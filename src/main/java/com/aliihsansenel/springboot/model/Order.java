package com.aliihsansenel.springboot.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Order extends Model {
	public int orderId;			
	public int itemId = -1;
	public int quantity = -1;
	public Date orderDate;
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	public String objectToJson() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
			return json.replaceFirst("Date\"\\s*:\\s*\\d*", 
					"Date:\" \"" + sdf.format(this.orderDate) + "\"");
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public Order jsonToObject(String json, boolean update) {
		//Pattern p = Pattern.compile(":\\s*\"?(\\d*|.*)[\\s\"}]");
		Pattern numbersP = Pattern.compile("\\d+\\D");
	    Matcher m = numbersP.matcher(json);
	    if(update && m.find())
	    	this.orderId = Integer.parseInt(json.substring(m.start(), m.end() - 1));
	    if(m.find())
	    	this.itemId = Integer.parseInt(json.substring(m.start(), m.end() - 1));
	    if(m.find())
	    	this.quantity = Integer.parseInt(json.substring(m.start(), m.end() - 1));
	    int index1 = json.substring(m.end()).indexOf('\"') + 12 + m.end();
	    index1 += json.substring(index1).indexOf('\"') + 1;
	    int index2 = json.substring(index1).indexOf('\"') + index1;
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    java.util.Date tempDate = null;
		try {
			System.out.println("patates:" + json.substring(index1, index2));
			tempDate = sdf.parse(json.substring(index1, index2));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	    this.orderDate = new Date(tempDate.getTime());
		return this;
	}
	@Override
	public Model toModel(ResultSet rs) {
		try {
			orderId = rs.getInt(1);
			itemId = rs.getInt(2);
			quantity = rs.getInt(3);
			orderDate = rs.getDate(4);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	public static String arrayToJson(ArrayList array) {
		ObjectMapper mapper = new ObjectMapper();
		
		StringBuilder builder = new StringBuilder("[\n");
		Iterator iterator = array.iterator();
		Order o;
		while(iterator.hasNext()) {
			o = (Order) iterator.next();
			builder.append("\n\t{\n\t\t\"orderId\": " + o.orderId);
			builder.append("\n\t\t\"itemId\": " + o.itemId);
			builder.append("\n\t\t\"quantity\": " + o.quantity);
			builder.append("\n\t\t\"orderDate\": \"" + sdf.format(o.orderDate));
			builder.append("\"\n\t},");
		}
		builder.setLength(Math.max(builder.length() - 1, 0));
		builder.append("\n]");
		return builder.toString();
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
}
