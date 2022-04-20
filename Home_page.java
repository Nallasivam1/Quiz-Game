package online.banking.pooling;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.sql.PooledConnection;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

public class Home_page {

	public static void main(String[] args) throws SQLException {
	boolean run=true;
	while(run) {
		OracleConnectionPoolDataSource ds=new OracleConnectionPoolDataSource();
		
		ds.setURL("jdbc:oracle:thin:@localhost:1521:xe");
		ds.setUser("System");
		ds.setPassword("password");
		
		PooledConnection pc=ds.getPooledConnection();
		
		
		Connection con=pc.getConnection();
		
		Statement st=con.createStatement();
		Scanner scan=new Scanner(System.in);
	System.out.println("Welcome to Online Banking Service");
	System.out.println("Enter Your Account Number");
	String account_number=scan.next();
	System.out.println("Enter Your Password");
	String password=scan.next();
	
	ResultSet rset=st.executeQuery("select account_number from banking_password where account_number='"+account_number+""+"' and password='"+password+""+"'");
	
	//ResultSet rset=st.executeQuery("select account_number from banking_password where account_number='"+account_number+"' and  password='"+password+"'");
	
if(rset.next()) {
	run=false;
	boolean accounts=true;
while(accounts) {
	ResultSet reset=st.executeQuery("select user_name from online_transaction where account_number='"+account_number+"'");
	Function user=new Function();
	reset.next();
	System.out.println("Welcome     "+reset.getString(1));
	System.out.println("\n");
	System.out.printf("\n%-60s","1.check Account Balance ");
	System.out.printf("%s","2.make payment ");
	System.out.printf("\n%-60s","3.Deposit Amount");
	System.out.printf("%s","4.view Transaction History");
	System.out.printf("\n%-60s","5.Add new Account");
	System.out.printf("%s","6.withdraw Amount");
	System.out.printf("\n%-60s","7.Change Account Password");
	System.out.printf("%s","8.exit \n \n ");
	System.out.println("Enter Your Choice...");
	int n=scan.nextInt();
	switch(n) {
	case 1:
		user.check_balance(account_number+"",st);
		break;
	case 2:
		System.out.println("Enter amout to pay");
		int amt=scan.nextInt();
		System.out.println("Enter account number to pay");
		String payment_account=scan.next();
		user.pay(account_number, amt,payment_account, st,user);
		break;
	case 3:
		System.out.println("Enter amout to deposit");
		int deposit1=scan.nextInt();
		user.deposit(account_number, deposit1, st);
		break;
	case 4:
		user.Transaction_history(account_number, st);
		break;
	case 5:
		user.add_account(st);
		break;
	case 6:
		System.out.println("Enter amout to withraw");
		int withdraw1=scan.nextInt();
		user.withdraw(account_number,withdraw1, st);
		break;
	case 7:
		user.changePassword(account_number, st);
		break;
	case 8:
		//System.exit(0);
		accounts=false;
		break;
	}

	}
}
else {
	System.out.println("Incorrect Account Number or Password");
	
}
			

		con.close();
	}
	}

}
