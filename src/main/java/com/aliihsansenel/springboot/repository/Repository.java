package com.aliihsansenel.springboot.repository;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.aliihsansenel.springboot.model.Customer;
import com.aliihsansenel.springboot.model.Model;

public abstract class Repository<M extends Model> {

	ArrayList<M> objects;
	Statement stmt = null;
	ResultSet rs;
	
	int tableSize = -1;

	public static Database db = new Database();
	
	boolean connected = false;

	public abstract ArrayList<M> getAll();

	public abstract M getById(int id);

	public abstract M saveOne(M model);
	
	public abstract M updateOne(M model);

	public abstract boolean deleteOne(int id);

	public abstract boolean checkIfExist(int id);
}
