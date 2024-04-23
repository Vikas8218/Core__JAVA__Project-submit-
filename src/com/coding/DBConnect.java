package com.coding;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
  private static final String url ="jdbc:mysql://127.0.0.1:3306/Market";
  private static final String user="root";
  private static final String pass="vikas1234";
   public static Connection getConnection() {
	   try {
		   Connection conn =DriverManager.getConnection(url,user,pass);
		   return conn;
	   }catch(Exception e) {
		   System.out.println(e);
	   }
	return null;
	   
   }
}
