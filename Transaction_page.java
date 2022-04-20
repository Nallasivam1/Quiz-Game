package online.banking.pooling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

abstract class Transaction_page {

	
			abstract int pay(String account_number,int amount,String payment_account, Statement st,Function user);
			abstract int deposit(String account_number,int amount, Statement st);
			abstract int check_balance(String account_number,Statement st);
			abstract int Transaction_history(String account_number, Statement st);
			abstract int add_account(Statement st);
			abstract int withdraw(String account_number,int amount, Statement st);
			abstract int changePassword(String account_number, Statement st);
			

		}
		

 class Function extends Transaction_page  {
			
			Scanner scan=new Scanner(System.in);
						
			int check_balance(String account_number, Statement st) {
				try {
				
						//String account_no=scan.next().trim();
						ResultSet rset=st.executeQuery("select balance from online_transaction where account_number='"+account_number+"'");
						while(rset.next()) {
							   System.out.println("Your Balance is :  "+rset.getInt(1)+" ");
							   
						   }
					
				 }
				 catch(Exception e) {
					    System.out.println(e);  
					  } 
						return 0;
			}

			@Override
			int Transaction_history(String account_number, Statement st) {
				ResultSet rset;
				try {
					rset = st.executeQuery("select * from A"+account_number+"");
					  System.out.println("	Date"+"			"+"Payment To account"+"			"+"Old Balance"+" 	 "+"New Balance"+" ");
						
					while(rset.next()) {
						   System.out.println(rset.getString(1)+"		 "+rset.getString(2)+"			 "+rset.getInt(3)+"		 "+rset.getInt(4)+" ");
						   
					   }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return 0;
			}

			
			int add_account(Statement st) {
				try {
					 
						System.out.println("Enter your User Name");
						String user_name=scan.next();
						System.out.println("Enter your mobile number");
						String mobile=scan.next();
						String account_number="15000"+mobile;
						int balance=0;
						ResultSet rset=st.executeQuery("insert into online_transaction values('"+user_name+"','"+account_number+"',"+balance+",'"+mobile+"')");
						ResultSet rset2=st.executeQuery("create table A"+account_number+"(date1 date,to_account varchar(20),old_balance number,new_balance number)");
						 System.out.println("ENTER THE  PASSWORD");
						 String password=scan.next();
							ResultSet rset12=st.executeQuery("insert into banking_password values("+account_number+","+password+")");
							 System.out.println(" PASSWORD UPDATED \n");
						System.out.println(" ACCOUNT CREATED SUCCESSFULLY");
				 }
				 catch(Exception e) {
					    System.out.println(e);  
					  } 
						return 0;
			}


			@Override
			int deposit(String account_number, int amount, Statement st) {
				try {
					ResultSet rset11=st.executeQuery("select balance from online_transaction where account_number='"+account_number+"'");
					rset11.next();
					int old_balance=rset11.getInt(1);
					int new_balance=old_balance+amount;
					ResultSet rset=st.executeQuery("update  online_transaction set balance=balance+"+amount+" where account_number="+account_number);
			
					ResultSet rset2=st.executeQuery("insert into A"+account_number+" values(sysdate,"+account_number+","+old_balance+","+new_balance+")");
					System.out.println("  SUCCESSFULLY DEPOSITED");
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
				return 0;
			}


			@Override
			int withdraw(String account_number, int amount,Statement st) {
				try {
					ResultSet rset11=st.executeQuery("select balance from online_transaction where account_number='"+account_number+"'");
					rset11.next();
					int old_balance=rset11.getInt(1);
					int new_balance=old_balance-amount;
					ResultSet rset=st.executeQuery("update  online_transaction set balance=balance-"+amount+" where account_number="+account_number);
				
				//	ResultSet rset=st.executeQuery("update  online_transaction set balance="+new_balance+" where account_number="+account_number);
					
					ResultSet rset2=st.executeQuery("insert into A"+account_number+" values(sysdate,"+account_number+","+old_balance+","+new_balance+")");
					System.out.println(" WITHDRAWAL SUCCESSFULL");
					
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				return 0;
			}


			@Override
			int pay(String account_number, int amount,String payment_account, Statement st, Function user) {
				try {
					ResultSet rset=st.executeQuery("select * from online_transaction where account_number="+payment_account+"");
					if(rset.next()) {
						
						user.withdraw(account_number, amount, st);
						//System.out.println("working");
						user.deposit(payment_account, amount, st);
						System.out.println(" PAYMENT SUCCESSFULL");
					}
					else {
						System.out.println("Payment Account Number Doesn't Exists");
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				return 0;
			}

			 int changePassword(String account_number, Statement st) {
				 System.out.println("ENTER THE NEW PASSWORD");
				 String password=scan.next();
				 try {
					ResultSet rset=st.executeQuery("update  banking_password set password="+password+" where account_number="+account_number);
					 System.out.println(" PASSWORD SUCCESSFULLY CHANGED");
				 }
				 catch (SQLException e) {
					e.printStackTrace();
				}
					
				 
				return 0;			
			}


			
			
		}
