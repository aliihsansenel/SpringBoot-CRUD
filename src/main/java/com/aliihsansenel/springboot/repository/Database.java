package com.aliihsansenel.springboot.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	Connection conn = null;
	Statement stmt = null;
	
	final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost:3306/prolab?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false";
	
    final String DB_USERNAME = "root";
    final String DB_PASSWORD = "password";
    
    public Database() {
    	try {
			Class.forName(JDBC_DRIVER);
			System.out.println("\nConnecting to database...");
	        conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	        System.out.println("Connected succesfully...");
	        
	        //System.out.println("1111: " + rs.getInt("id"));
	        
	        
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("\nCouldn't connecto to database!");
			e.printStackTrace();
		}
    	
    }
    public Statement getStatement() {
    	if(stmt != null) {
    		return stmt;
    	}
    	System.out.println("\nCreating statement...\n");
        try {
			stmt = conn.createStatement();
			//stmt.executeQuery("delete from ratings where CustomerID = 7 and ItemID = 7;");
		} catch (SQLException e) {
			System.out.println("\nFailed to create statement!");
			e.printStackTrace();
		}
		return stmt;
    }
    public void closeConnection() {
    	if(stmt != null) {
    		try {
    			stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }
}
