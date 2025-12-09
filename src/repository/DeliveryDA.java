package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Delivery;
import model.OrderHeader;
import utils.Connect;

public class DeliveryDA {
	
    // Mengambil koneksi database
	private Connection conn = Connect.getInstance().getConn();

    
    // Menambahkan courier ke order
	public boolean assignCourierToOrder(String idOrder, String idCourier) {
		String query = "INSERT INTO delivery (idOrder, idCourier) VALUES (?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(query);

            // Set ID order dan ID courier
			ps.setString(1, idOrder);
			ps.setString(2, idCourier);

            // Eksekusi insert dan kembalikan hasil
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace(); 
			return false;
		}
	}

    
    // Mengambil seluruh delivery untuk courier tertentu
	public ArrayList<OrderHeader> getAllDeliveries(String idCourier){
		ArrayList<OrderHeader> allDelivery = new ArrayList<>();
		
		String query = "SELECT * FROM order_header oh JOIN delivery d ON oh.idOrder = d.idOrder WHERE d.idCourier = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, idCourier);

            ResultSet rs = ps.executeQuery();

            // Looping semua hasil dan masukkan ke list
            while (rs.next()) {
                allDelivery.add(new OrderHeader(
                        rs.getString("idOrder"),
                        rs.getString("idCustomer"),
                        rs.getString("idPromo"),        
                        rs.getString("status"),
                        rs.getTimestamp("ordered_at"),
                        rs.getDouble("total_amount")));
            }
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		return allDelivery; // Kembalikan list
	}

    
    // Mengambil delivery dengan status Pending untuk courier tertentu
	public ArrayList<OrderHeader> getDeliveries(String idCourier){
		ArrayList<OrderHeader> assignDelivery = new ArrayList<>();
		
		String query = "SELECT * FROM order_header oh JOIN delivery d ON oh.idOrder = d.idOrder WHERE d.idCourier = ? AND oh.status = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, idCourier);
            ps.setString(2, "Pending");

            ResultSet rs = ps.executeQuery();

            // Looping hasil dan masukkan ke list
            while (rs.next()) {
                assignDelivery.add(new OrderHeader(
                        rs.getString("idOrder"),
                        rs.getString("idCustomer"),
                        rs.getString("idPromo"),        
                        rs.getString("status"),
                        rs.getTimestamp("ordered_at"),
                        rs.getDouble("total_amount")));
            }
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		return assignDelivery; // Kembalikan list
	}
	
}
