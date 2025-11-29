package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Customer;
import utils.Connect;

public class CustomerDA {

    private Connection conn = Connect.getInstance().getConn();

    public boolean saveDA(Customer customer) {
        try {
            String query = "UPDATE users SET balance = ? WHERE idUser = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, customer.getBalance());
            ps.setString(2, customer.getIdUser());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}