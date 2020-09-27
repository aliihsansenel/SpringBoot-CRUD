package com.aliihsansenel.springboot.repository;

import java.sql.SQLException;
import java.util.ArrayList;

import com.aliihsansenel.springboot.model.Item;

public class ItemRepository extends Repository<Item> {

	public ItemRepository() {
		try {
			tableSize = db.getStatement()
					.executeQuery(String.format("select Count(*) from %s;", "items"))
					.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public ArrayList<Item> getAll() {
		stmt = db.getStatement();
		
		try {
			rs = stmt.executeQuery(String.format("select * from %s;", "items"));
			objects = new ArrayList<Item>(tableSize + 4);
			while(rs.next()) {
				Item i = new Item();
				i.toModel(rs);
				objects.add(i);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objects;
	}

	@Override
	public Item getById(int id) {
		stmt = db.getStatement();
		Item i = null;
		try {
			rs = stmt.executeQuery(String.format("select * from %s where ItemID = %d;"
					, "items", id));
			if(rs.next()) {
				i = new Item();
				i.toModel(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public Item saveOne(Item item) {
		stmt = db.getStatement();
		Item i = null;
		try {
			System.out.println(String.format("Price: \"%.2f\"", item.price));
			int affectedRows = stmt.executeUpdate(String.format("insert into %s (%s, %s, %s, %s) "
					+ "values (\"%s\", \"%s\", \"%d\", \"%s\")"
					, "items", "ItemName", "Supplier", "StockQuantity", "Price",
					item.itemName, item.supplier, item.stockQuantity, 
					String.format("%.2f", item.price).replace(',', '.') ));
			System.out.println("affectedRows:" + affectedRows);
			if(affectedRows == 1) {
				rs = stmt.executeQuery(String.format("select * from %s order by %s desc limit 1"
						, "items", "ItemID"));
				tableSize++;
			}
			if(rs.next()) {
				i = new Item();
				i.toModel(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public Item updateOne(Item item) {
		stmt = db.getStatement();
		Item i = getById(item.itemId);
		if(item.itemName != null)	i.itemName = item.itemName;
		if(item.supplier != null)	i.supplier = item.supplier;
		if(item.stockQuantity != -1)	i.stockQuantity = item.stockQuantity;
		if(item.price != -1)	i.price = item.price;
		
		try {
			int affectedRows = stmt.executeUpdate(String.format("update %s set %s = \"%s\", %s = \"%s\","
					+ " %s = \"%d\", %s = \"%s\" "
					+ "where %s = %d;"
					, "items", "ItemName", i.itemName, "Supplier", i.supplier,  "StockQuantity", i.stockQuantity
					, "Price", String.format("%.2f", item.price).replace(',', '.'), "ItemID", i.itemId));
			System.out.println("affectedRowns:" + affectedRows);
			if(affectedRows == 1) {
				rs = stmt.executeQuery(String.format("select * from %s where %s = %d"
						, "items", "ItemID", i.itemId));
				
			}
			if(rs.next()) {
				i = new Item();
				i.toModel(rs);
				return i;
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
					, "items", "ItemID", id));
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
			rs = stmt.executeQuery(String.format("select Count(*) from %s where ItemID = %d;"
					, "items", id));
			if(rs.next() && rs.getInt(1) == 1) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
