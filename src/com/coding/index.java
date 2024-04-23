package com.coding;

import java.util.Scanner;

public class index {
	public static void main(String args[]) {
		Scanner sc =new Scanner(System.in);
		while(true) {
        System.out.println(   "|Welcome Super_Market__System|");
			System.out.println("|******* 1. ADMIN ADD *******|");
			System.out.println("|******* 2. Admin LOGIN *****|");
			System.out.println("|******* 3. CUSTOMER REGISTER|");
			System.out.println("|******* 4. CUSTOMER LOGIN **|");
			System.out.println("|******* 5. EXIT ************|");
			System.out.println("      Enter your choice :     ");
			int choice =sc.nextInt();
			switch(choice) {
			case 1 :
			  ADMIN.AddAdmin(sc);
				break;
			case 2 :
				ADMIN.AdminLogin(sc);
				break;
			case 3:
				CUSTOMER.Addcustomer(sc);
				break;
			case 4 :
				CUSTOMER.CustomerLogin(sc);
				break;
			case 5 :
				System.out.println("/n Thank You");
				System.exit(0);
				break;
			}
			
		}
	}
	
	
}
