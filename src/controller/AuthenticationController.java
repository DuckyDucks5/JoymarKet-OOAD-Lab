package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Admin;
import model.Customer;
import model.User;
import utils.Connect;

public class AuthenticationController {
	private final java.sql.Connection conn = Connect.getInstance().getConn();

	public String registerCustomerWithValidation(String fullname, String email, String password, String confirm,
		String phone, String address) {
		StringBuilder sb = new StringBuilder();

		if (fullname.isEmpty())
			sb.append("- Full Name cannot be empty.\n");

		if (email.isEmpty()) {
			sb.append("- Email must be filled.\n");
		} else if (!email.endsWith("@gmail.com"))
			sb.append("- Email must end with @gmail.com.\n");

		if (password.length() < 6) {
			sb.append("- Password must be at least 6 characters.\n");
		}
		if (!password.equals(confirm)) {
			sb.append("- Confirm password must match Password.\n");
		}

		if (phone.isEmpty()) {
			sb.append("- Phone must be filled.\n");
		} else {
			if (!phone.matches("\\d+")) {
				sb.append("- Phone must be numeric.\n");
			}
			if (phone.length() < 10 || phone.length() > 13) {
				sb.append("- Phone must be 10–13 digits.\n");
			}
		}

		if (address.isEmpty()) {
			sb.append("- Address must be filled.\n");
		}

		if (sb.length() > 0) {
			return sb.toString();
		}

		try {
			double balance = 0;
			String newId = generateCustomerID();
			Customer tempCustomer = new Customer(newId, fullname, email, password, phone, address, balance);
			Customer registered = registerCustomer(tempCustomer);

			if (registered != null) {
				return "SUCCESS";
			} else {
				return "Something went wrong while creating your account.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}
	}

	public User login(String email, String password) {
		if (email == null || password == null || email.isBlank() || password.isBlank()) {
			System.out.println("Email or password cannot be empty.");
			return null;
		}

		String query = "SELECT * FROM users WHERE email = ? AND password = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, email);
			ps.setString(2, password);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next())
					return null;

				String id = rs.getString("idUser");
				String fullname = rs.getString("fullName");
				String phone = rs.getString("phone");
				String address = rs.getString("address");
				String role = rs.getString("role");
				double balance = rs.getDouble("balance");
				String emergency = rs.getString("emergencyContact");

				if ("admin".equalsIgnoreCase(role)) {
					return new Admin(id, fullname, email, password, phone, address, emergency);
				} else {
					return new Customer(id, fullname, email, password, phone, address, balance);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String generateCustomerID() {
		return new UserController().generateCustomerID();
	}

	private Customer registerCustomer(Customer customer) {
		return new UserController().registerCustomer(customer);
	}
}
