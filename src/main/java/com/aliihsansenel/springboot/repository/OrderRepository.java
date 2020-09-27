package com.aliihsansenel.springboot.repository;

import java.sql.SQLException;
import java.util.ArrayList;

import com.aliihsansenel.springboot.model.Customer;
import com.aliihsansenel.springboot.model.Item;
import com.aliihsansenel.springboot.model.Model;
import com.aliihsansenel.springboot.model.Order;

public class OrderRepository extends Repository<Order> {
	public OrderRepository() {
		try {
			tableSize = db.getStatement()
					.executeQuery(String.format("select Count(*) from %s;", "orders"))
					.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<Order> getAll() {
		stmt = db.getStatement();
		
		try {
			rs = stmt.executeQuery(String.format("select * from %s;", "orders"));
			objects = new ArrayList<Order>(tableSize + 4);
			while(rs.next()) {
				Order o = new Order();
				o.toModel(rs);
				objects.add(o);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objects;
	}
	
	@Override
	public Order getById(int id) {
		stmt = db.getStatement();
		Order o = null;
		try {
			rs = stmt.executeQuery(String.format("select * from %s where orderId = %d;"
					, "orders", id));
			if(rs.next()) {
				o = new Order();
				o.toModel(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	@Override
	public Order saveOne(Order order) {
		stmt = db.getStatement();
		Order o = null;
		try {
			System.out.println("saveOne:orderDate=" + order.orderDate);
			int affectedRows = stmt.executeUpdate(String.format("insert into %s (%s, %s, %s) "
					+ "values (\"%s\", \"%d\", \"%s\")"
					, "orders", "ItemID", "Quantity", "OrderDate", 
					order.itemId, order.quantity, order.orderDate));
			System.out.println("affectedRowns:" + affectedRows);
			if(affectedRows == 1) {
				rs = stmt.executeQuery(String.format("select * from %s order by %s desc limit 1"
						, "orders", "orderID"));
				tableSize++;
			}
			if(rs.next()) {
				o = new Order();
				o.toModel(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	@Override
	public Order updateOne(Order order) {
		stmt = db.getStatement();
		Order o = getById(order.orderId);
		if(order.itemId != -1)	o.itemId = order.itemId;
		if(order.quantity != -1)	o.quantity = order.quantity;
		if(order.orderDate != null)	o.orderDate = order.orderDate;
		System.out.println("updateOne:orderDate=" + o.orderDate);
		try {
			int affectedRows = stmt.executeUpdate(String.format("update %s set %s = \"%d\", %s = \"%d\","
					+ " %s = \"%s\" "
					+ "where %s = %d;"
					, "orders", "ItemID", o.itemId, "Quantity", o.quantity,  "OrderDate", o.orderDate, "OrderID", o.orderId));
			System.out.println("affectedRowns:" + affectedRows);
			if(affectedRows == 1) {
				rs = stmt.executeQuery(String.format("select * from %s where %s = %d"
						, "orders", "OrderID", o.orderId));
				
			}
			if(rs.next()) {
				o = new Order();
				o.toModel(rs);
				return o;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean deleteOne(int id) {
		try {
			int affectedRows = stmt.executeUpdate(String.format("delete from %s where %s = \"%s\""
					, "orders", "OrderID", id));
			if(affectedRows == 1) {
				tableSize--;
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkIfExist(int id) {
		stmt = db.getStatement();
		try {
			rs = stmt.executeQuery(String.format("select Count(*) from %s where OrderID = %d;"
					, "orders", id));
			if(rs.next() && rs.getInt(1) == 1) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
