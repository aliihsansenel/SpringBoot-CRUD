package com.aliihsansenel.springboot.repository;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;

import com.aliihsansenel.springboot.model.Customer;
import com.aliihsansenel.springboot.model.OrderListItem;

public class OrderListRepository extends Repository<OrderListItem>{

	public OrderListRepository() {
		try {
			tableSize = db.getStatement()
					.executeQuery(String.format("select Count(*) from %s;", "orderlist"))
					.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public ArrayList<OrderListItem> getAll() {
		stmt = db.getStatement();
		
		try {
			rs = stmt.executeQuery(String.format("select * from %s;", "orderlist"));
			objects = new ArrayList<OrderListItem>(tableSize + 4);
			while(rs.next()) {
				OrderListItem i = new OrderListItem();
				i.toModel(rs);
				objects.add(i);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objects;
	}

	@Override
	public OrderListItem getById(int id) {
		return null;
	}

	@Override
	public OrderListItem saveOne(OrderListItem listitem) {
		stmt = db.getStatement();
		OrderListItem i = null;
		try {
			int affectedRows = stmt.executeUpdate(String.format("insert into %s "
					+ "values (\"%d\", \"%d\")"
					, "orderlist", listitem.customerId, listitem.orderId));
			System.out.println("affectedRowns:" + affectedRows);
			if(affectedRows == 1) {
				rs = stmt.executeQuery(String.format("select * from %s where OrderID = %d;"
						, "orderlist", listitem.orderId));
				tableSize++;
			}
			if(rs.next()) {
				i = new OrderListItem();
				i.toModel(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public OrderListItem updateOne(OrderListItem listitem) {
		return null;
	}

	@Override
	public boolean deleteOne(int id) {
		try {
			int affectedRows = stmt.executeUpdate(String.format("delete from %s where %s = %d"
					, "orderlist", "OrderID", id));
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
					, "orderlist", id));
			if(rs.next() && rs.getInt(1) == 1) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}

