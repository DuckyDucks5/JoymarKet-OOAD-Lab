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
	
	private Connection conn = Connect.getInstance().getConn();
	
	public boolean assignCourierToOrder(String idOrder, String idCourier) {
		String query = "INSERT INTO delivery (idOrder, idCourier)"
				+ "VALUES (?, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setString(1, idOrder);
			ps.setString(2, idCourier);
			
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<OrderHeader> getAllDeliveries(String idCourier){
		ArrayList<OrderHeader> allDelivery = new ArrayList<>();
		
		String query = "SELECT * FROM order_header oh JOIN delivery d ON oh.idOrder = d.idOrder WHERE d.idCourier = ?";
		
		try {
			 
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, idCourier);

            ResultSet rs = ps.executeQuery();

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
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return allDelivery;
	}
	
	public ArrayList<OrderHeader> getDeliveries(String idCourier){
		ArrayList<OrderHeader> assignDelivery = new ArrayList<>();
		
		String query = "SELECT * FROM order_header oh JOIN delivery d ON oh.idOrder = d.idOrder WHERE d.idCourier = ? AND oh.status = ?";
		
		try {
			 
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, idCourier);
            ps.setString(2, "Waiting for Delivery");

            ResultSet rs = ps.executeQuery();

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
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return assignDelivery;
	}
	
}
