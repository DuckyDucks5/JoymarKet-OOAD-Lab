package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import model.Admin;
import model.Customer;
import model.User;
import utils.Connect;

public class UserRepo {
	private Connection conn = Connect.getInstance().getConn();

	public Customer registerCustomer(Customer customer) {
		try {
			String newId = generateCustomerID();
			customer.setIdUser(newId);

			String query = "INSERT INTO users (idUser, fullName, email, password, phone, address, balance, role) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(query);

			ps.setString(1, customer.getIdUser());
			ps.setString(2, customer.getFullname());
			ps.setString(3, customer.getEmail());
			ps.setString(4, customer.getPassword());
			ps.setString(5, customer.getPhone());
			ps.setString(6, customer.getAddress());
			ps.setDouble(7, customer.getBalance());
			ps.setString(8, "customer");

			int affected = ps.executeUpdate();
			if (affected == 0)
				return null;

			return new Customer(newId, customer.getFullname(), customer.getEmail(), customer.getPassword(),
					customer.getPhone(), customer.getAddress(), customer.getBalance());

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public String generateCustomerID() {
		try {
			String lastId = null;
			int num = 1;

			while (true) {
				String query = "SELECT idUser FROM users WHERE idUser = ?";
				PreparedStatement ps = conn.prepareStatement(query);

				if (lastId == null) {
					ps.setString(1, "CU" + String.format("%03d", num));
				} else {
					ps.setString(1, lastId);
				}
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					return "CU" + String.format("%03d", num);
				}
				num++;
				lastId = "CU" + String.format("%03d", num);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public User login(String email, String password) {
		try {
			String query = "SELECT * FROM users WHERE email = ? AND password = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				String id = rs.getString("idUser");
				String fullname = rs.getString("fullName");
				String phone = rs.getString("phone");
				String address = rs.getString("address");
				String role = rs.getString("role");

				double balance = rs.getDouble("balance");
				String emergency = rs.getString("emergencyContact");

				if (role.equalsIgnoreCase("admin")) {
					return new Admin(id, fullname, email, password, phone, address, emergency);
				} else {
					return new Customer(id, fullname, email, password, phone, address, balance);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean updateBalance(String idUser, double newBalance) {
		try {
			String query = "UPDATE users SET balance = ? WHERE idUser = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setDouble(1, newBalance);
			ps.setString(2, idUser);
			int affected = ps.executeUpdate();
			return affected > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
