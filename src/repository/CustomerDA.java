package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Customer;
import utils.Connect;

public class CustomerDA {

    // Mengambil koneksi database
    private Connection conn = Connect.getInstance().getConn();

    
    // Menyimpan/Update saldo customer
    public boolean saveDA(Customer customer) {
        try {
            String query = "UPDATE users SET balance = ? WHERE idUser = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set nilai balance dan ID customer
            ps.setDouble(1, customer.getBalance());
            ps.setString(2, customer.getIdUser());

            // Eksekusi update dan kembalikan hasil
            return ps.executeUpdate() > 0;
            
        } catch (Exception e) {
            e.printStackTrace(); // Tangani exception
            return false;
        }
    }
 
}
