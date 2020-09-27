package com.aliihsansenel.springboot.date;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class DateTest {

	public static void main(String[] args) throws SQLException {
		DateTest dt = new DateTest();
		
		ResultSet rs =  dt.getStatement().executeQuery("select OrderDate from orders where OrderID = 2");
		rs.next();
		Date date = rs.getDate(1);
		System.out.println(String.format("Tarih: %s", date));
		dt.closeConnection();
		

		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		System.out.println("Tarih: " + sdf.format(date));
		
		
	}

	Connection conn = null;
	Statement stmt = null;
	
	final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final String DB_URL = "jdbc:mysql://localhost:3306/prolab?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false";
	
    final String DB_USERNAME = "root";
    final String DB_PASSWORD = "password";
    
    public DateTest() {
    	try {
			Class.forName(JDBC_DRIVER);
			System.out.println("\nConnecting to database...");
	        conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
	        System.out.println("Connected succesfully...");
	        //stmt.executeQuery("SELECT * FROM musteriler");
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
