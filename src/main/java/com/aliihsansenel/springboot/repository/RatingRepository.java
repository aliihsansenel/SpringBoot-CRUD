package com.aliihsansenel.springboot.repository;

import java.sql.SQLException;
import java.util.ArrayList;

import com.aliihsansenel.springboot.model.Rating;
import com.aliihsansenel.springboot.model.RatingsJoined;

public class RatingRepository extends Repository<Rating> {

	public RatingRepository() {
		try {
			tableSize = db.getStatement()
					.executeQuery(String.format("select Count(*) from %s;", "ratings"))
					.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public ArrayList<Rating> getAll() {
		stmt = db.getStatement();
		
		try {
			rs = stmt.executeQuery(String.format("select * from %s;", "ratings"));
			objects = new ArrayList<Rating>(tableSize + 4);
			while(rs.next()) {
				Rating r = new Rating();
				r.toModel(rs);
				objects.add(r);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objects;
	}
	public ArrayList<RatingsJoined> getAllJoined() {
		stmt = db.getStatement();
		ArrayList<RatingsJoined> objects = null;
		try {
			rs = stmt.executeQuery("SELECT c.CustomerID, c.Firstname, c.Sirname, i.ItemID, i.ItemName, r.rating "
					+ "from ratings r "
					+ "inner join customers c "
					+ "on c.CustomerID = r.CustomerID "
					+ "inner join items i "
					+ "on i.ItemID = r.ItemID;");
			objects = new ArrayList<RatingsJoined>(100);
			while(rs.next()) {
				RatingsJoined rj = new RatingsJoined();
				rj.toModel(rs);
				objects.add(rj);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return objects;
	}
	public Rating getById(int customerId, int itemId) {
		stmt = db.getStatement();
		Rating r = null;
		try {
			rs = stmt.executeQuery(String.format("select * from %s where CustomerID = %d AND ItemID = %d;"
					, "ratings", customerId, itemId));
			if(rs.next()) {
				r = new Rating();
				r.toModel(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public Rating saveOne(Rating rating) {
		stmt = db.getStatement();
		Rating r = null;
		try {
			int affectedRows = stmt.executeUpdate(String.format("insert into %s "
					+ "values (\"%d\", \"%d\", \"%s\")"
					, "ratings", rating.customerId, rating.itemId, 
					String.format("%.2f", rating.rating).replace(',', '.') ));
			System.out.println("affectedRows:" + affectedRows);
			if(affectedRows == 1) {
				rs = stmt.executeQuery(String.format("select * from %s where %s = %d and %s = %d;"
						, "ratings", "CustomerID", rating.customerId, "ItemID", rating.itemId));
				tableSize++;
			}
			if(rs.next()) {
				r = new Rating();
				r.toModel(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public Rating updateOne(Rating rating) {
		stmt = db.getStatement();
		Rating r = getById(rating.customerId, rating.itemId);
		if(rating.customerId != -1)	r.customerId = rating.customerId;
		if(rating.itemId != -1)	r.itemId = rating.itemId;
		if(rating.rating != -1)	r.rating = rating.rating;
		
		try {
			int affectedRows = stmt.executeUpdate(String.format("update %s set %s = \"%s\" "
					+ "where %s = %d and %s = %d;", "ratings", "Rating", String.format("%.2f", r.rating).replace(',', '.')
					, "CustomerID", r.customerId, "ItemID", r.itemId));
			System.out.println("affectedRowns:" + affectedRows);
			if(affectedRows == 1) {
				rs = stmt.executeQuery(String.format("select * from %s where %s = %d and %s = %d;"
						, "ratings", "CustomerID", r.customerId, "ItemID", r.itemId));
				
			}
			if(rs.next()) {
				r = new Rating();
				r.toModel(rs);
				return r;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean deleteOne(int customerId, int itemId) {
		try {
			int affectedRows = stmt.executeUpdate(String.format("delete from %s where %s = %d and %s = %d;"
					, "ratings",  "CustomerID", customerId, "ItemID", itemId));
			if(affectedRows >= 1) {
				tableSize -= affectedRows;
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean checkIfExist(int customerId, int itemId) {
		stmt = db.getStatement();
		try {
			rs = stmt.executeQuery(String.format("select Count(*) from %s where CustomerID = %d and ItemID = %d;"
					, "ratings", customerId, itemId));
			if(rs.next() && rs.getInt(1) >= 1) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Rating getById(int id) {
		return null;
	}
	@Override
	public boolean deleteOne(int id) {
		return false;
	}
	@Override
	public boolean checkIfExist(int id) {
		return false;
	}

}
