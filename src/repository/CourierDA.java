package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Courier;
import utils.Connect;

public class CourierDA {

    // Mengambil koneksi database
    private Connection conn = Connect.getInstance().getConn();

    
    // Mengambil seluruh data courier
    public ArrayList<Courier> getAllCouriers(){
        ArrayList<Courier> couriers = new ArrayList<>();
        
        String query = "SELECT * FROM users WHERE idUser LIKE ? AND role = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            // Set filter ID dan role courier
            ps.setString(1, "CR%");
            ps.setString(2, "courier");

            ResultSet rs = ps.executeQuery();

            // Looping hasil query dan masukkan ke list
            while(rs.next()) {
                couriers.add(new Courier(
                    rs.getString("idUser"),
                    rs.getString("fullName"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("vehicleType"),
                    rs.getString("vehiclePlate")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Tangani exception SQL
        }

        return couriers; // Kembalikan list courier
    }

    
    // Mengubah status order menjadi Waiting for Delivery
    public boolean assignCourier(String orderId) {
        String query = "UPDATE order_header SET status = ? WHERE idOrder = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            // Set status dan ID order
            ps.setString(1, "Waiting for Delivery"); 
            ps.setString(2, orderId);

            int rows = ps.executeUpdate();
            return rows > 0; // Return true jika update berhasil

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    // Mendapatkan ID courier untuk order tertentu
    public String getCourier(String idOrder) {
        String query = "SELECT d.idCourier "
                + "FROM delivery d JOIN order_header oh ON d.idOrder = oh.idOrder "
                + "WHERE d.idOrder = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            // Set ID order
            ps.setString(1, idOrder);

            ResultSet rs = ps.executeQuery();

            // Jika ditemukan, kembalikan ID courier
            if(rs.next()) {
                return rs.getString("idCourier");
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Tangani exception SQL
        }

        return null; // Return null jika tidak ditemukan
    }
}
