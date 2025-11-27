package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;

import utils.Connect;

public class CustomerRepo {

    private Connection conn = Connect.getInstance().getConn();

    public boolean topUp(String customerId, double amount) {
        try {
            String query = "UPDATE users SET balance = balance + ? WHERE idUser = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDouble(1, amount);
            ps.setString(2, customerId);

            int updated = ps.executeUpdate();
            return updated > 0; // SUCCESS
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
