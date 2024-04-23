package com.coding;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class CUSTOMER {
static Scanner sc =new Scanner(System.in);
	public static void Addcustomer(Scanner sc) {
		System.out.println("Enter a Email id :");
		String Eid =sc.next();
		System.out.println("Enter a password :");
		String pass =sc.next();
		System.out.println("Enter a frist name :");
		String fname =sc.next();
		System.out.println("Enter a last name :");
		String lname =sc.next();
		System.out.println("Enter a Mobile :");
		String mob =sc.next();
		System.out.println("Enter a City name :");
		String cname =sc.next();
		System.out.println("Enter a gender :");
		String  gender=sc.next();
		try {
			 
			 Connection conn =DBConnect.getConnection();
			 String sql ="INSERT INTO CUSTOMER(Eid,pass,fname,lname,mob,cname,gender) value(?,?,?,?,?,?,?)";
			 PreparedStatement pst =conn.prepareStatement(sql);
			 pst.setString(1, Eid);
			 pst.setString(2,pass);
			 pst.setString(3, fname);
			 pst.setString(4, lname);
			 pst.setString(5, mob);
			 pst.setString(6, cname);
			 pst.setString(7, gender);
			 int ar=pst.executeUpdate();
			 if(ar>0) {
				 System.out.println("Customer Details Successfully Added !");
			 }
				 else
					 System.out.println("Admin Already Exist on");
			 
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
	// check CUSTOMER in database
		public static int CheckCUSTOMER(String Eid,String pass,String roles) {
			try {
				Connection conn =DBConnect.getConnection();
				String sql ="SELECT * FROM CUSTOMER WHERE Eid = ? AND pass = ? AND roles = ? AND sts = 1";
				PreparedStatement pst =conn.prepareStatement(sql);
				pst.setString(1, Eid);
				pst.setString(2, pass);
				pst.setString(3, roles);
				ResultSet rs =pst.executeQuery();
				if(rs.next()) {
					System.out.println("Customer login Sucessfull !");
					return rs.getInt(1);
				}
					else
						System.out.println("Customer details invaild / Customer Unactive !!!");
						return 0;
				
			}catch(Exception e) {
				System.out.println(e);
			}
			return 0;
		}
		// Customer Login page
		public static void CustomerLogin(Scanner sc) {
			System.out.println("Enter Admin Email:");
			String  Eid =sc.next();
			System.out.println("Enter Admin  pass :");
			String pass =sc.next();
			int id =CheckCUSTOMER(Eid,pass,"CUSTOMER");
			if(id>0) {
				int flag =1;
				while(flag==1) {
					System.out.println("|~~~~CUSTOMER PANEL LOGIN~~~~~|");
					System.out.println("|******** 1. View Products ***|");
					System.out.println("|******** 2. Buy product *****|");
					System.out.println("|******** 3. View Orders *****|");
					System.out.println("|******** 4. Exit ************|");
					System.out.println("       Enter Your Choice :     ");
					int choice =sc.nextInt();
					switch(choice) {
					case 1 :
						ViewProducts();
						break;
					case 2 :
						Buyproduct(id);
						break;
					case 3 :
						ViewOrders(id);
						break;
					case 4 : 
						System.out.println("\n****Bye--bye--Customer\n");
						flag=0;
						break;
						default :
							System.out.println("\nCustomer Email or password is incorrect !");
					}
				}
				
			}
		}
		// View Products 
		public static void ViewProducts() {
			try {
			    Connection conn = DBConnect.getConnection();
			    String sql = "SELECT * FROM AddProduct";
			    PreparedStatement pst = conn.prepareStatement(sql);
			    ResultSet rs = pst.executeQuery();

			    System.out.println("+---+-----------------+----------+-----------+--------------+");
			    System.out.println("|ID |PRODUCT NAME     |EXDATE    |QUANTITY   |PRODUCT PRICE |");
			    System.out.println("+---+-----------------+----------+-----------+--------------+");

			    while (rs.next()) {
			        System.out.printf("|%2d|%18s|%10s|%11d|%14s|\n", rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
			    }

			    System.out.println("+---+----------------+------------+-----------+-------------+");
			} catch (Exception e) {
			    System.out.println(e);
			}

		}
		// Buy product
		public static void Buyproduct(int id) {
		    System.out.println("Enter a Product id:");
		    int pid = sc.nextInt();
		    
		    try {
		        Connection conn = DBConnect.getConnection();
		        String SQL = "SELECT * FROM AddProduct WHERE id=?";
		        PreparedStatement pst = conn.prepareStatement(SQL);
		        pst.setInt(1, pid);
		        ResultSet rs = pst.executeQuery();
		        if (rs.next()) {
		            int quantity = rs.getInt("quantity");
		            
		            if (quantity > 0) {
		                String SQL2 = "INSERT INTO Buyproduct(cid, pid) VALUES (?, ?)";
		                PreparedStatement pp = conn.prepareStatement(SQL2);
		               pp.setInt(1, id);
		               pp.setInt(2, pid);
		                int rowsInserted = pp.executeUpdate();
		                if (rowsInserted > 0) {
		                    String updateProductSQL = "UPDATE AddProduct SET quantity = ? WHERE id = ?";
		                    PreparedStatement updateProductStmt = conn.prepareStatement(updateProductSQL);
		                    updateProductStmt.setInt(1, quantity - 1);
		                    updateProductStmt.setInt(2, pid);
		                    int rowsUpdated = updateProductStmt.executeUpdate();
		                    
		                    if (rowsUpdated > 0) {
		                        System.out.println("Order buy successful!");
		                    } else {
		                        System.out.println("Failed to update product quantity!");
		                    }
		                } else {
		                    System.out.println("Failed to insert into Buyproduct table!");
		                }
		            } else {
		                System.out.println("Product quantity is zero. Try after some time!");
		            }
		        } else {
		            System.out.println("Product with ID or customer with CID not found!");
		        }
		    } catch (Exception e) {
		        System.out.println(e);
		    }
		}

		// View Orders
		public static void ViewOrders(int id) {
		    try {
		     String sql ="select  buyproduct.bid,customer.cid,buyproduct.pid,customer.fname,customer.gender,addproduct.productname,addproduct.exdate, addproduct.proprice from customer  join Buyproduct on customer.cid=buyproduct.cid join addproduct on buyproduct.pid=addproduct.id where customer.cid=?";
		        PreparedStatement pst = DBConnect.getConnection().prepareStatement(sql);
		        pst.setInt(1, id);
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
		}

}
	

