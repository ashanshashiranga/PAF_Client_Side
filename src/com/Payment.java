package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Payment {

	private Connection connect() {
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/paf", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPayments(String CardNo, String expDate, String cvv, String amount) {
		String output = "";
		
		try {
			
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = " insert into payment(`paymentId`,`cardNumber`,`expireDate`,`cvv`,`paymentAmount`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, CardNo);
			preparedStmt.setString(3, expDate);
			preparedStmt.setString(4, cvv);
			preparedStmt.setDouble(5, Double.parseDouble(amount));

			// execute the statement3
			preparedStmt.execute();
			con.close();
			
			String newPayments = readPayments(); 
			 output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";
			
		} catch (Exception e) {
			
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the Payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPayments() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			
			// Prepare the html table to be displayed
			output = "<table border='1'> <tr><th>Card Number</th><th>Expiry Date</th>" + "<th>CVV</th>"	+ "<th>Payment Amount</th>" + "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String paymentId = Integer.toString(rs.getInt("paymentId"));
				String cardNumber = rs.getString("cardNumber");
				String expireDate = rs.getString("expireDate");
				String cvv = Integer.toString(rs.getInt("cvv"));
				String paymentAmount = Double.toString(rs.getDouble("paymentAmount"));

				// Add into the html table
				output += "<tr><td><input id='hidPaymentIDUpdate' name='hidPaymentIDUpdate' type='hidden' value='" + paymentId+ "'>" + "</td>"; 
				output += "<td>" + cardNumber + "</td>";
				output += "<td>" + expireDate + "</td>";
				output += "<td>" + cvv + "</td>";
				output += "<td>" + paymentAmount + "</td>"; 
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>" 
				+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-paymentid='"
				 + paymentId + "'>" + "</td></tr>"; 
			}
			
			con.close();
			
			// Complete the html table
			output += "</table>";
			
		} catch (Exception e) {
			output = "Error while reading the Payments.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}

	
	public String updatePayments(String ID, String CardNo, String expDate, String CVV, String amount) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				
				return "Error while connecting to the database for updating.";
			}
			
			// create a prepared statement
			String query = "UPDATE payment SET cardNumber=?,expireDate=?,cvv=?,paymentAmount=? WHERE paymentId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, CardNo);
			preparedStmt.setString(2, expDate);
			preparedStmt.setInt(3, Integer.parseInt(CVV));
			preparedStmt.setDouble(4, Double.parseDouble(amount));
			preparedStmt.setInt(5, Integer.parseInt(ID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPayments = readPayments(); 
			 output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}"; 
			
		} catch (Exception e) {
			
			output = "{\"status\":\"error\", \"data\": \"Error while updating the Payment.\"}"; 
			
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePayments(String paymentId) {
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if (con == null) {
				
				return "Error while connecting to the database for deleting.";
			}
			
			// create a prepared statement
			String query = "delete from payment where paymentId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(paymentId));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newPayments = readPayments(); 
			 output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}"; 
			
		} catch (Exception e) {
			
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the Payment.\"}";
			
			System.err.println(e.getMessage());
		}
		return output;
	}

}
