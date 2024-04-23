package com.coding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ADMIN {
	 // add admin 
	public static void AddAdmin(Scanner sc) {
		System.out.println("Enter Aadhaarid :");
		String Aadhaarid =sc.next();
		System.out.println("Enter Shopid:");
		String Shopid =sc.next();
		System.out.println("Enter a Name :");
		String name =sc.next();
		System.out.println("Enter a Mobile No :");
		String mob =sc.next();
		System.out.println("Enter Address  :");
		String address =sc.next();
		System.out.println("Enter Gender :");
		String gender =sc.next();
		 try {
			 
			 Connection conn =DBConnect.getConnection();
			 String sql ="INSERT INTO admin(Aadhaarid,Shopid,name,mob,address,gender) value(?,?,?,?,?,?)";
			 PreparedStatement pst =conn.prepareStatement(sql);
			 pst.setString(1, Aadhaarid);
			 pst.setString(2, Shopid);
			 pst.setString(3, name);
			 pst.setString(4, mob);
			 pst.setString(5, address);
			 pst.setString(6, gender);
			 int ar=pst.executeUpdate();
			 if(ar>0) {
				 System.out.println("Admin details Successfully !");
			 }
				 else
					 System.out.println("Admin Already Exist on");
			 
		 }catch(Exception e) {
			 System.out.println(e);
		 }
	}
	// check admin in database
	public static int CheckAdmin(String Aadhaarid,String Shopid,String roles) {
		try {
			Connection conn =DBConnect.getConnection();
			String sql ="SELECT * FROM Admin WHERE Aadhaarid =? AND Shopid=? AND roles=? AND sts=1";
			PreparedStatement pst =conn.prepareStatement(sql);
			pst.setString(1, Aadhaarid);
			pst.setString(2, Shopid);
			pst.setString(3, roles);
			ResultSet rs =pst.executeQuery();
			if(rs.next()) {
				System.out.println("Admin Login successfull !!");
				return rs.getInt(1);
			}
				else
					System.out.println("Admin password  incorect !!");
					return 0;
			
		}catch(Exception e) {
			System.out.println(e);
		}
		return 0;
	}
	// Admin Login page
	public static void AdminLogin(Scanner sc) {
		System.out.println("Enter Admin Aadhaarid :");
		String  shopid =sc.next();
		System.out.println("Enter Admin  Shopid :");
		String  pass=sc.next();
		if(CheckAdmin(shopid,pass, "Admin")>0) {
			int flag =1;
			while(flag==1) {
				System.out.println("|~~~~~~~~~~ADMIN LOGIN~~~~~~~~~~~~~~~|");
				System.out.println("|******** 1. Add Product ************|");
				System.out.println("|******** 2. Update Product *********|");
				System.out.println("|******** 3. Delete PRoduct *********|");
				System.out.println("|******** 4. Active/Unactive Customer|");
				System.out.println("|******** 5. View orders ************|");
				System.out.println("|******** 6. Exit *******************|");
				System.out.println("          Enter your choice :        ");
				int choice =sc.nextInt();
				switch(choice) {
				case 1 :
					AddProduct(sc);
					break;
				case 2 :
					UpdateProduct(sc);				
					break;
				case 3 :
					DeleteProduct(sc);
					break;
				case 4 :
					ActiveUnactiveCustomer(sc);
					break;
				case 5 :
					ViewOrders();
					break;
				case 6 :
					System.out.println("\n******Bye__Bye Admin********");
					flag=0;
					break;
				default :
					System.out.println("*****try Again!*****");
				}}
		}else {
				     System.out.println("\n Admin Aadhaar  or Shopid is incorrect");
				
				}
				
			}
	//   Add Product 
	public static void AddProduct(Scanner sc) {
		
		System.out.println("Enter a product name :");
		String productname =sc.next();
		System.out.println("Enter a product Expire Date :");
		String ExDate=sc.next();
		System.out.println("Enter Quantity :");
		String Quantity =sc.next();
	    System.out.println(" Product price");
	    String proprice =sc.next();
		try {
			String sql ="INSERT INTO AddProduct(productname,ExDate,Quantity,proprice)value(?,?,?,?)";
			PreparedStatement pst =DBConnect.getConnection().prepareStatement(sql);
			pst.setNString(1, productname);
			pst.setNString(2, ExDate);
			pst.setNString(3, Quantity);
			pst.setString(4, proprice);
			int ar=pst.executeUpdate();
			if(ar>0) {
				System.out.println("Product add successfully!!");
			}
				else
					System.out.println("Failed To Add!");
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	 // Update Product 
	   public static void UpdateProduct(Scanner sc) {
		   System.out.println("Enter a Update productname :");
		   String upproduct =sc.next();
		   System.out.println("Enter a Update ExpDate :");
		   String UpExpDate =sc.next();
		   System.out.println("Enter a update Quantity :");
		   String UpQuantity =sc.next();
		   System.out.println("Enter a id number :");
		   int id =sc.nextInt();
		   System.out.println("Product price :");
		   String proprice =sc.next();
		    try {
		    	String sql ="UPDATE AddProduct Set Productname =? , ExDate=?,Quantity=?,proprice=? WHERE id =?";
		    	PreparedStatement pst =DBConnect.getConnection().prepareStatement(sql);
		    	pst.setString(1,upproduct);
		    	pst.setString(2,UpExpDate);
		    	pst.setString(3,UpQuantity);
		    	pst.setString(4, proprice);
		    	pst.setInt(5, id);
		    	int abc =pst.executeUpdate();
		    	if(abc>0)
		    		System.out.println("AND Update Successfully !!");
		    	else
		    		System.out.println("Failed To Return !!\n");
		   
	   }catch(Exception e) {
		   System.out.println(e);
	   }
	   }
	   //  Delete Product 
	   public static void DeleteProduct(Scanner sc) {
		    System.out.println("Enter a product id:");
		    String id =sc.next();
		    try {
		    	String sql ="DELETE FROM AddProduct WHERE id=?";
		    	PreparedStatement pst =DBConnect.getConnection().prepareStatement(sql);
		    	pst.setString(1,id);
		    	int abc =pst.executeUpdate();
		    	if(abc>0)
		    		System.out.println("AND Delete Successfully !!");
		    	else
		    		System.out.println("Failed To Return !!\n");
		    	
		    	
		    }catch(Exception e) {
		    	System.out.println(e);
		    }
	   }
	   // Active/ Unactive Customer
	   public static void ActiveUnactiveCustomer(Scanner sc) {
		   System.out.println("Enter a Customer id :");
		   String cid =sc.next();
		   try {
			   int sts=-1;
			   System.out.println("Press 1 to Active");
			   System.out.println("Press 0 ot Unactive");
			   int ch =sc.nextInt();
			   if(ch==1) 
				   sts =1;
			   
				   else if(ch==0)
					   sts=0;
				   else
					   System.out.println("Wrong Enterd !/n");
			   if((sts==1)||(sts==0)) {
				   String st ="UPDATE CUSTOMER SET sts=? WHERE cid=?";
				   PreparedStatement pst =DBConnect.getConnection().prepareStatement(st);
				   pst.setInt(1,sts);
			    	pst.setString(2,cid);
			    	int abc =pst.executeUpdate();
			    	if(abc>0)
			    		System.out.println("press  Successfully !!");
			    	else
			    		System.out.println("Failed To Return !!\n");
			   }
					  
			   
		   }catch(Exception e) {
			   System.out.println(e);
		   }
		   
	   }
	   
	// View Orders
			public static void ViewOrders() {
			    try {
			       
			     String sql ="select buyproduct.bid,customer.cid,buyproduct.pid,customer.fname,customer.gender,addproduct.productname,addproduct.exdate, addproduct.proprice from addproduct Inner join customer on addproduct.id=customer.cid join buyproduct on buyproduct.bid=addproduct.id";
			        PreparedStatement pst = DBConnect.getConnection().prepareStatement(sql);
			        ResultSet rs = pst.executeQuery();
			        
			        System.out.println("+-------+--------------+-----------+--------------+-----------+----------------------+-------------+----------------+");
			        System.out.println("|Buy ID | Customer ID  | Product ID| First Name   | Gender    | Product name         | ExpDate     | Product Price  |");
			        System.out.println("+-------+--------------+-----------+--------------+-----------+----------------------+-------------+----------------+");

			        while (rs.next()) {
			            System.out.printf("|%7d|%14d|%11d|%14s|%11s|%22s|%13s|%16s|\n",
			             rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8));                 
			        }
			        System.out.println("+---+-------+--------------+-----------+--------------+-----------+----------------------+-------------+----------------+");
			    } catch (Exception e) {
			        System.out.println("Error executing SQL query:");
			        e.printStackTrace();
			   
			    }
			}}
