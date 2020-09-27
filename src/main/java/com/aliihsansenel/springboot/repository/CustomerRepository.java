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

public class CustomerRepository extends Repository<Customer>{

	public CustomerRepository() {
		try {
			tableSize = db.getStatement()
					.executeQuery(String.format("select Count(*) from %s;", "customers"))
					.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public ArrayList<Customer> getAll() {
		stmt = db.getStatement();
		
		try {
			rs = stmt.executeQuery(String.format("select * from %s;", "customers"));
			objects = new ArrayList<Customer>(tableSize + 4);
			while(rs.next()) {
				Customer c = new Customer();
				c.toModel(rs);
				objects.add(c);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objects;
	}

	@Override
	public Customer getById(int id) {
		stmt = db.getStatement();
		Customer c = null;
		try {
			rs = stmt.executeQuery(String.format("select * from %s where CustomerID = %d;"
					, "customers", id));
			if(rs.next()) {
				c = new Customer();
				c.toModel(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public Customer saveOne(Customer customer) {
		stmt = db.getStatement();
		Customer c = null;
		try {
			int affectedRows = stmt.executeUpdate(String.format("insert into %s (%s, %s, %s) "
					+ "values (\"%s\", \"%s\", \"%s\")"
					, "customers", "Firstname", "Sirname", "Address", 
					customer.firstname, customer.sirname, customer.address));
			System.out.println("affectedRowns:" + affectedRows);
			if(affectedRows == 1) {
				rs = stmt.executeQuery(String.format("select * from %s order by %s desc limit 1"
						, "customers", "CustomerID"));
				tableSize++;
			}
			if(rs.next()) {
				c = new Customer();
				c.toModel(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	@Override
	public Customer updateOne(Customer customer) {
		stmt = db.getStatement();
		Customer c = getById(customer.customerId);
		if(customer.firstname != null)	c.firstname = customer.firstname;
		if(customer.sirname != null)	c.sirname = customer.sirname;
		if(customer.address != null)	c.address = customer.address;
		
		try {
			int affectedRows = stmt.executeUpdate(String.format("update %s set %s = \"%s\", %s = \"%s\", %s = \"%s\" "
					+ "where %s = %d"
					, "customers", "Firstname", c.firstname, "Sirname", c.sirname,  "Address", c.address, "CustomerID", c.customerId));
			System.out.println("affectedRowns:" + affectedRows);
			if(affectedRows == 1) {
				rs = stmt.executeQuery(String.format("select * from %s where %s = %d"
						, "customers", "CustomerID", c.customerId));
				
			}
			if(rs.next()) {
				c = new Customer();
				c.toModel(rs);
				return c;
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
					, "customers", "CustomerID", id));
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
			rs = stmt.executeQuery(String.format("select Count(*) from %s where CustomerID = %d;"
					, "customers", id));
			if(rs.next() && rs.getInt(1) == 1) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	
}
