package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Customer;
import model.User;
import model.Admin;
import model.Courier;
import utils.Connect;

public class UserDA {

    private Connection conn = Connect.getInstance().getConn();

    //REGISTER CUSTOMER
    public Customer insertCustomer(Customer customer) {
        try {
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
            if (affected > 0) {
                return customer;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //GENERATE MANUAL ID CUSTOMER
    public String generateCustomerID() {
        try {
            int num = 1;
            while (true) {
                String id = "CU" + String.format("%03d", num);
                String query = "SELECT idUser FROM users WHERE idUser = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, id);

                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    return id;
                }
                num++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
    //Find Email Password for Login Validate
    public User findByEmailAndPassword(String email, String password) {
        try {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) return null;

            String id = rs.getString("idUser");
            String fullname = rs.getString("fullName");
            String phone = rs.getString("phone");
            String address = rs.getString("address");
            String role = rs.getString("role");
            double balance = rs.getDouble("balance");
            String emergency = rs.getString("emergencyContact");
            String vehicleType = rs.getString("vehicleType");
            String vehiclePlate = rs.getString("vehiclePlate");

            if (role.equalsIgnoreCase("admin")) {
                return new Admin(id, fullname, email, password, phone, address, emergency);
            }
            else if (role.equalsIgnoreCase("courier")) {
            	return new Courier(id, fullname, email, phone, address, vehicleType, vehiclePlate);
            }

            return new Customer(id, fullname, email, password, phone, address, balance);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //UPDATE BALANCE CUSTOMER
    public boolean updateBalance(String idUser, double newBalance) {
        try {
            String query = "UPDATE users SET balance = ? WHERE idUser = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, newBalance);
            ps.setString(2, idUser);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
