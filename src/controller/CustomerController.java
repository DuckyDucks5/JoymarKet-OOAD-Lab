package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Customer;
import utils.Connect;

public class CustomerController {
	private Connection conn = Connect.getInstance().getConn();

	public String topUp(Customer customer, double amount) {
		if (amount < 10000) {
			return "Minimum top-up amount is 10,000!";
		}

		double newBalance = customer.getBalance() + amount;

		try {
			String query = "UPDATE users SET balance = ? WHERE idUser = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setDouble(1, newBalance);
			ps.setString(2, customer.getIdUser());

			int updated = ps.executeUpdate();
			if (updated > 0) {
				customer.setBalance(newBalance);
				return "SUCCESS";
			} else {
				return "Failed to update balance. Please try again.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}
	}
}
