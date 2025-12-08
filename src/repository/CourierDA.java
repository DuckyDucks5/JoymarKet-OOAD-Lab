package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Courier;
import utils.Connect;

public class CourierDA {
	private Connection conn = Connect.getInstance().getConn();
	
	public ArrayList<Courier> getAllCouriers(){
		ArrayList<Courier> couriers = new ArrayList<>();
		
		String query = "SELECT * FROM users WHERE idUser LIKE ? AND role = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, "CR%");
			ps.setString(2, "courier");
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String idUser = rs.getString("idUser");
				String fullName = rs.getString("fullName");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				String address = rs.getString("address");
				String vehicleType = rs.getString("vehicleType");
				String vehiclePlate = rs.getString("vehiclePlate");
				couriers.add(new Courier(idUser, fullName, email, phone, address, vehicleType, vehiclePlate));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return couriers;
	}
	
	public boolean assignCourier(String orderId) {
		
		String query = "UPDATE order_header SET status = ? WHERE idOrder = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
//            ps.setString(1, courierId);
            ps.setString(1, "Waiting for Delivery"); 
            ps.setString(2, orderId);

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public String getCourier(String idOrder) {
		String query = "SELECT d.idCourier "
				+ "FROM delivery d JOIN order_header oh ON d.idOrder = oh.idOrder "
				+ "WHERE d.idOrder = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, idOrder);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getString("idCourier");
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
