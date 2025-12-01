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
}
