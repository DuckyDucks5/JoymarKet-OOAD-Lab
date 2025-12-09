package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.OrderDetail;
import utils.Connect;

public class OrderDetailDA {

    // Mengambil koneksi database
	private Connection conn = Connect.getInstance().getConn();

    
    // Menambahkan detail order baru
    public boolean createOrderDetail(OrderDetail detail) {
        try {
            String query = "INSERT INTO order_detail (idOrder, idProduct, qty) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set parameter order detail
            ps.setString(1, detail.getIdOrder());
            ps.setString(2, detail.getIdProduct());
            ps.setInt(3, detail.getQty());

            // Eksekusi insert dan kembalikan hasil
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace(); // Tangani exception
            return false;
        }
    }

    
    // Mengambil seluruh detail order berdasarkan ID order
    public ArrayList<OrderDetail> getOrderDetails(String idOrder) {
        ArrayList<OrderDetail> list = new ArrayList<>();

        try {
            String query = "SELECT * FROM order_detail WHERE idOrder = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, idOrder);

            ResultSet rs = ps.executeQuery();

            // Looping hasil query dan masukkan ke list
            while (rs.next()) {
                list.add(new OrderDetail(
                        rs.getString("idOrder"),
                        rs.getString("idProduct"),
                        rs.getInt("qty")));
            }

        } catch (Exception e) {
            e.printStackTrace(); // Tangani exception
        }

        return list; // Kembalikan list detail order
    }

    
    // Menghapus seluruh detail order berdasarkan ID order
    public boolean deleteDetails(String orderId) {
        try {
            String query = "DELETE FROM order_detail WHERE idOrder = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, orderId);

            // Eksekusi delete dan kembalikan hasil
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace(); // Tangani exception
            return false;
        }
    }
}
